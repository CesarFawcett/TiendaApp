package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.OrdenCompra;

/**
 * Repositorio para la gestión de entidades {@link OrdernCompra}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see OrdernCompra
 */

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    
}
