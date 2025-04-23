package edu.unimag.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Producto;

@Component
public class ProductoMapper {
    public ProductoCreateDto toProductoCreateDto(Producto productoCreated){
        ProductoCreateDto productoCreateDto = new ProductoCreateDto();
        productoCreateDto.setId(productoCreated.getId());
        productoCreateDto.setNombre(productoCreated.getNombre());
        productoCreateDto.setDescripcion(productoCreated.getDescripcion());
        productoCreateDto.setPrecio(productoCreated.getPrecio());
        productoCreateDto.setStock(productoCreated.getStock());
        productoCreateDto.setFecha(productoCreated.getFecha());
        productoCreateDto.setCategoria(productoCreated.getCategoria());
        return productoCreateDto;
    }
    public static List<ProductoDto> toDtoList(List<Producto> productos){
        List<ProductoDto> productoDtos = new ArrayList<>();
        for (Producto producto : productos){
            ProductoDto productoDto = new ProductoDto();

            productoDto.setId(producto.getId());
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
    public ProductoDto toProductoDto(Producto createdProducto){
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(createdProducto.getId());
        productoDto.setNombre(createdProducto.getNombre());
        productoDto.setDescripcion(createdProducto.getDescripcion());
        productoDto.setPrecio(createdProducto.getPrecio());
        productoDto.setStock(createdProducto.getStock());
        productoDto.setFecha(createdProducto.getFecha());
        productoDto.setCategoria(createdProducto.getCategoria());
        return productoDto;
    }
    public Producto toProductoDto(ProductoCreateDto productoDto) {
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecio(productoDto.getPrecio());
        producto.setStock(productoDto.getStock());
        producto.setFecha(productoDto.getFecha());
        producto.setCategoria(productoDto.getCategoria());
        return producto;
       }
}
