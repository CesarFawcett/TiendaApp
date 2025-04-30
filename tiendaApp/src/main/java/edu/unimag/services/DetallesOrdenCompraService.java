package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.DetallesOrdenCompra;

public interface DetallesOrdenCompraService {
    List<DetallesOrdenCompra> findAll();
    DetallesOrdenCompra create (DetallesOrdenCompra newDetallesOrdenCompra);
    Optional<DetallesOrdenCompra> findById(Long id);
    DetallesOrdenCompra update(Long id, DetallesOrdenCompra newDetallesOrdenCompra);
    void delete(Long id);
}
