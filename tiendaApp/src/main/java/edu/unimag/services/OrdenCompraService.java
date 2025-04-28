package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.OrdenCompra;

public interface OrdenCompraService {
    
    List<OrdenCompra> findAll();
    OrdenCompra create (OrdenCompra newOrdenCompra);
    Optional<OrdenCompra> findById(Long id);
    OrdenCompra update(long id, OrdenCompra newOrdenCompra);
    void delete(Long id);

}
