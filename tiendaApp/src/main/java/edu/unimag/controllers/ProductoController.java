package edu.unimag.controllers;

import edu.unimag.dto.ProductoCreateDto;
import edu.unimag.dto.ProductoDto;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.entities.Producto;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.ProductoService;
import edu.unimag.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; 
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;

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
        // Obtener la categoría (asegúrate de que la categoría con ID 1 exista)
        var categoria = categoriaService.findById(1L)
                .orElseThrow(() -> new RuntimeException("Categoría con ID 1 no encontrada"));

        ProductoCreateDto productoCreateDto = new ProductoCreateDto();
        productoCreateDto.setNombre("papa criolla");
        productoCreateDto.setDescripcion("también llamada papa amarilla");
        productoCreateDto.setPrecio(20.5);
        productoCreateDto.setFecha(java.time.LocalDate.of(2025, 4, 29));
        productoCreateDto.setStock(20);
        Producto producto = productoMapper.toProducto(productoCreateDto);
        producto.setCategoria(categoria); 
        productoService.create(producto);
    }

    @PostMapping
    public ResponseEntity<ProductoDto> createProducto(@Valid @RequestBody ProductoCreateDto productoCreateDto) {
        // Obtener la categoría
        var categoria = categoriaService.findById(productoCreateDto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoría no encontrada"));
        Producto producto = productoMapper.toProducto(productoCreateDto);
        producto.setCategoria(categoria); // Asignar la categoría
        Producto createdProducto = productoService.create(producto);
        return new ResponseEntity<>(productoMapper.toProductoDto(createdProducto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productoMapper.toDtoList(productos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable Long id) {
        return productoService.findById(id)
                .map(productoMapper::toProductoDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoCreateDto productoCreateDto) {
        // Obtener la categoría
        var categoria = categoriaService.findById(productoCreateDto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoría no encontrada"));
        try {
            Producto producto = productoMapper.toProducto(productoCreateDto);
            producto.setCategoria(categoria); // Asignar la categoría
            Producto updatedProducto = productoService.update(id, producto);
            return ResponseEntity.ok(productoMapper.toProductoDto(updatedProducto));
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de producto inválidos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}