package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimag.entities.HistorialEstadoOrden;

public interface HistorialEstadoOrdenRepository extends JpaRepository<HistorialEstadoOrden, Long> {
    
}
