package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.HistorialEstadoOrden;

public interface HistorialEstadoOrdenService {
    List<HistorialEstadoOrden> findAll();
    HistorialEstadoOrden create(HistorialEstadoOrden newHistorialEstadoOrden);
    Optional<HistorialEstadoOrden> findById(Long id);
    void delete(Long id);
}
