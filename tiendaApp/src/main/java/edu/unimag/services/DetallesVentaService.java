package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.DetallesVenta;

public interface DetallesVentaService {
    List<DetallesVenta> findAll();
    DetallesVenta create(DetallesVenta newDetallesVenta);
    Optional<DetallesVenta> findById(Long id);
    DetallesVenta update(long id, DetallesVenta newDetallesVenta);
    void delete(Long id);
}
