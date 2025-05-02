package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unimag.dto.DetallesVentaCreateDto;
import edu.unimag.entities.DetallesVenta;
import edu.unimag.entities.Producto;
import edu.unimag.entities.Venta;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.repositories.DetallesVentaRepository;
import edu.unimag.repositories.ProductoRepository;
import edu.unimag.repositories.VentaRepository;
import edu.unimag.services.VentaService;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final DetallesVentaRepository detallesVentaRepository;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            ProductoRepository productoRepository,
                            DetallesVentaRepository detallesVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.detallesVentaRepository = detallesVentaRepository;
    }

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

    @Override
    public Venta addDetalle(Long ventaId, DetallesVentaCreateDto dto) {
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new EntidadNoEncontradaException("Venta", ventaId));

        Producto producto = productoRepository.findById(dto.getProductoId())
            .orElseThrow(() -> new EntidadNoEncontradaException("Producto", dto.getProductoId()));

        DetallesVenta detalle = new DetallesVenta();
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setProducto(producto);
        detalle.setVenta(venta);

        detallesVentaRepository.save(detalle);
        venta.addDetalle(detalle);

        return ventaRepository.save(venta);
    }

    @Override
    public List<DetallesVenta> getDetalles(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new EntidadNoEncontradaException("Venta", ventaId));

        return venta.getDetalles();
    }
    
}
