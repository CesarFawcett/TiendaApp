package edu.unimag.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Producto;

@Component
public class ProductoMapper {
    public ProductoCreateDto toProductoCreateDto(Producto producto){
        ProductoCreateDto productoCreateDto = new ProductoCreateDto();
        productoCreateDto.setNombre(producto.getNombre());
        productoCreateDto.setDescripcion(producto.getDescripcion());
        productoCreateDto.setPrecio(producto.getPrecio());
        productoCreateDto.setStock(producto.getStock());
        productoCreateDto.setFecha(producto.getFecha());
        productoCreateDto.setCategoria(producto.getCategoria());
        return productoCreateDto;
    }
    public static List<ProductoDto> toDtoList(List<Producto> productos){
        List<ProductoDto> productoDtos = new ArrayList<>();
        for (Producto producto : productos){
            ProductoDto productoDto = new ProductoDto();

            productoDto.setNombre(producto.getNombre());
            productoDto.setDescripcion(producto.getDescripcion());
            productoDto.setPrecio(producto.getPrecio());
            productoDto.setStock(producto.getStock());
            productoDto.setFecha(producto.getFecha());
            productoDto.setCategoria(producto.getCategoria());

            productoDtos.add(productoDto);
        }
        return productoDtos;
    }
    public ProductoDto toProductoDto(Producto producto){
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre(producto.getNombre());
        productoDto.setDescripcion(producto.getDescripcion());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setStock(producto.getStock());
        productoDto.setFecha(producto.getFecha());
        productoDto.setCategoria(producto.getCategoria());
        return productoDto;
    }
}
