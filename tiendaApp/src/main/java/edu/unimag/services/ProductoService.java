package edu.unimag.services;

import java.util.List;
import edu.unimag.entities.Producto;

public interface ProductoService {
    
    List<Producto> findAll();
    void delete(Long id);
    Producto create(Producto newProducto);
}
