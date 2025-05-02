package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.EstadoOrden;

public interface EstadoOrdenService {
    List<EstadoOrden> findAll();
    Optional<EstadoOrden> findByName(String name);
}
