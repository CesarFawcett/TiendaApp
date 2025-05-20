package edu.unimag.repositories;

import java.util.List;

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

    List<DetallesVenta> findByVentaId(Long ventaId);
    
}
