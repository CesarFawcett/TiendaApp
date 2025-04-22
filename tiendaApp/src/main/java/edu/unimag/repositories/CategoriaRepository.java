package edu.unimag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.entities.Categoria;

/**
 * Repositorio para la gestión de entidades {@link Categoria}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Categoria
 */

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombre(String string);
    
}
