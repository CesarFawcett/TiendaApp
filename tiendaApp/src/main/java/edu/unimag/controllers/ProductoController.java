package edu.unimag.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.dto.ProductoCreateDto;
import edu.unimag.dto.ProductoDto;
import edu.unimag.entities.Categoria;
import edu.unimag.entities.Producto;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.CategoriaService;
import edu.unimag.services.ProductoService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
        this.categoriaService = categoriaService;
    }

    @PostConstruct
    public void initSampleProducto() {
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 1. Obtener la categoría con ID 1 usando la instancia de CategoriaService
        Categoria categoria1 = categoriaService.findById(1L)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2. Crear el producto
        Producto producto1 = new Producto();
        producto1.setNombre("papa criolla");
        producto1.setDescripcion("también llamada papa amarilla");
        producto1.setPrecio(20.5);
        producto1.setFecha(LocalDate.parse("2025-04-29 11:30", dataFormatter));
        producto1.setStock(20);
        producto1.setCategoria(categoria1);

        productoService.create(producto1);
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoCreateDto productoCreateDto) {
        Producto producto = productoMapper.toProductoDto(productoCreateDto);
        Producto createdProducto = productoService.create(producto);
        ProductoDto productoDto = productoMapper.toProductoDto(createdProducto);
        return ResponseEntity.ok(productoDto);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        List<Producto> productos = productoService.findAll(); 
        List<ProductoDto> productoDto = productoMapper.toDtoList(productos);
        return ResponseEntity.ok(productoDto);
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.findById(id);
        if (producto.isPresent()) {
            ProductoDto productoDto = productoMapper.toProductoDto(producto.get());
            return ResponseEntity.ok(productoDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
     public ResponseEntity<ProductoDto> updateProducto(@PathVariable Long id,@Valid @RequestBody ProductoCreateDto productoCreateDto) {
        Producto producto = productoMapper.toProductoDto(productoCreateDto); // Map DTO to Entity
        try {
            Producto updatedProducto = productoService.update(id, producto); // Call service with entity
            return ResponseEntity.ok(productoMapper.toProductoDto(updatedProducto)); // Map updated entity to DTO and return
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or return a more specific error DTO
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
