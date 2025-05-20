package edu.unimag.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Venta;

/**
 * Repositorio para la gestión de entidades {@link Venta}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Venta
 */

public interface VentaRepository extends JpaRepository<Venta, Long>{

    List<Venta> findTop30ByOrderByFechaDesc();
    
}
