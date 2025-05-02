package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Venta;

public interface VentaService {
    List<Venta> findAll();
    Venta create(Venta newVenta);
    Optional<Venta> findById(Long id);
    Venta update(Long id, Venta newVenta);
    void delete(Long id);
}
