package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.unimag.entities.Venta;
import edu.unimag.repositories.VentaRepository;
import edu.unimag.services.VentaService;

public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta create(Venta newVenta) {
        return ventaRepository.save(newVenta);
    }

    @Override
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta update(Long id, Venta newVenta) {
        Optional<Venta> existingVenta = ventaRepository.findById(id);
          if (existingVenta.isPresent()) {
              Venta ventaToUpdate = existingVenta.get();
              ventaToUpdate.setFecha(newVenta.getFecha());
              ventaToUpdate.setUsuario(newVenta.getUsuario());
              ventaToUpdate.setDetalles(newVenta.getDetalles());
              ventaToUpdate.setCliente(newVenta.getCliente());
              return ventaRepository.save(ventaToUpdate);
          } else {
              return null;  
          }
    }

    @Override
    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }
    
}
