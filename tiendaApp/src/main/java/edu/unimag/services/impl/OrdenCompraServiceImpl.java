package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.entities.OrdenCompra;
import edu.unimag.repositories.OrdenCompraRepository;
import edu.unimag.services.OrdenCompraService;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Override
    public List<OrdenCompra> findAll() {
        return ordenCompraRepository.findAll();
        }

    @Override
    public OrdenCompra create(OrdenCompra newOrdenCompra) {
        return ordenCompraRepository.save(newOrdenCompra);
        }

    @Override
    public Optional<OrdenCompra> findById(Long id) {
        return ordenCompraRepository.findById(id);
       }

    @Override
    public OrdenCompra update(long id, OrdenCompra newOrdenCompra) {
    Optional<OrdenCompra> existingOrdenCompra = ordenCompraRepository.findById(id);
    if (existingOrdenCompra.isPresent()){
        OrdenCompra ordenCompraToUpdate = existingOrdenCompra.get();
        ordenCompraToUpdate.setFecha(newOrdenCompra.getFecha());
        ordenCompraToUpdate.setEstado(newOrdenCompra.getEstado());
        ordenCompraToUpdate.setProveedor(newOrdenCompra.getProveedor());
        return ordenCompraRepository.save(ordenCompraToUpdate);
    }else {
        return null;
    }
    }

    @Override
    public void delete(Long id) {
        ordenCompraRepository.deleteById(id);
       }
}
