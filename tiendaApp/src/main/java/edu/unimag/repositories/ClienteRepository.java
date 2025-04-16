package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Cliente;

/**
 * Repositorio para la gestión de entidades {@link Cliente}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Cliente
 */

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
