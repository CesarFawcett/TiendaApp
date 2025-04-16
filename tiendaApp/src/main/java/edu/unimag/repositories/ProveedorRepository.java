package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Proveedor;

/**
 * Repositorio para la gestión de entidades {@link Proveedor}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Proveedor
 */

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
}
