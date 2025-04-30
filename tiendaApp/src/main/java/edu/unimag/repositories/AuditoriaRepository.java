package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Auditoria;

/**
 * Repositorio para la gestión de ajuditorias {@link Auditoria}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Auditoria
 */

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    
}
