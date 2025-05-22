package edu.unimag.services;

import java.util.List;
import java.util.Optional;

import edu.unimag.dto.ProductoMasVendidoDto;
import edu.unimag.entities.Producto;

public interface ProductoService {
    List<Producto> findAll();
    Producto create(Producto newProducto);
    Optional<Producto> findById(Long id);
    Producto update(Long id, Producto newProducto);
    void delete(Long id);
    List<ProductoMasVendidoDto> findTopSellingProducts(int i);
}
