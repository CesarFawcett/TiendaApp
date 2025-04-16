package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Usuario;

/**
 * Repositorio para la gestión de entidades {@link Usuario}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Usuario
 */

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
