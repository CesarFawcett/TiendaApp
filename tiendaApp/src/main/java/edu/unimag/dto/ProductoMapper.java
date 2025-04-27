package edu.unimag.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Producto;

@Component
public class ProductoMapper {

    public ProductoDto toProductoDto(Producto producto) {
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(producto.getId());
        productoDto.setNombre(producto.getNombre());
        productoDto.setDescripcion(producto.getDescripcion());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setStock(producto.getStock());
        productoDto.setFecha(producto.getFecha());
        return productoDto;
    }

    public Producto toProducto(ProductoCreateDto productoCreateDto) {
        Producto producto = new Producto();
        producto.setNombre(productoCreateDto.getNombre());
        producto.setDescripcion(productoCreateDto.getDescripcion());
        producto.setPrecio(productoCreateDto.getPrecio());
        producto.setStock(productoCreateDto.getStock());
        producto.setFecha(productoCreateDto.getFecha());
        producto.setCategoria(null); // La categoría se maneja en el servicio/controlador
        return producto;
    }

    public List<ProductoDto> toDtoList(List<Producto> productos) {
        return productos.stream()
                .map(this::toProductoDto)
                .collect(Collectors.toList());
    }
}