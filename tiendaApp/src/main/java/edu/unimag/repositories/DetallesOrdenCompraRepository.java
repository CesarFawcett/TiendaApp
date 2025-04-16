package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.DetallesOrdenCompra;

/**
 * Repositorio para la gestión de entidades {@link DetallesOrdenCompra}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see DetallesOrdenCompra
 */

public interface DetallesOrdenCompraRepository extends JpaRepository<DetallesOrdenCompra, Long> {
    
}
