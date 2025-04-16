package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.DetallesVenta;

/**
 * Repositorio para la gestión de entidades {@link DetallesVenta}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see DetallesVenta
 */

public interface DetallesVentaRepository extends JpaRepository<DetallesVenta, Long>{
    
}
