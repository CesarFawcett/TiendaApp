package edu.unimag.services;

import java.util.List;
import java.util.Optional;

import edu.unimag.dto.DetallesVentaCreateDto;
import edu.unimag.entities.DetallesVenta;
import edu.unimag.entities.Venta;

public interface VentaService {
    List<Venta> findAll();
    Venta create(Venta newVenta);
    Optional<Venta> findById(Long id);
    Venta update(Long id, Venta newVenta);
    void delete(Long id);
    Venta addDetalle(Long ventaId, DetallesVentaCreateDto dto);
    List<DetallesVenta> getDetalles(Long ventaId);
}
