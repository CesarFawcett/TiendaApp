package edu.unimag.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.unimag.dto.ProductoMasVendidoDto;
import edu.unimag.entities.Producto;

/**
 * Repositorio para la gestión de entidades {@link Producto}.
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository} 
 * y permite la ejecución de consultas personalizadas.
 * </p>
 * 
 * @see JpaRepository
 * @see Producto
 */

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByStockLessThan(int stock);

}
