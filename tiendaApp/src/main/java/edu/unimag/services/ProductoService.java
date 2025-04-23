package edu.unimag.services;

import java.util.List;
import java.util.Optional;

import edu.unimag.entities.Producto;

public interface ProductoService {
    
    List<Producto> findAll();
    void delete(Long id);
    Producto create(Producto newProducto);
    Optional<Producto> findById(Long id);
}
