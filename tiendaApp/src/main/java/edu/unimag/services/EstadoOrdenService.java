package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.EstadoOrden;

public interface EstadoOrdenService {
    List<EstadoOrden> findAll();
    EstadoOrden create(EstadoOrden newEstadoOrden);
    Optional<EstadoOrden> findById(Long id);
    EstadoOrden update(Long id, EstadoOrden newEstadoOrden);
    void delete(Long id);
}
