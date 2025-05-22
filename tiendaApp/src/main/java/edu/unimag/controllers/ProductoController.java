package edu.unimag.controllers;

import edu.unimag.dto.ProductoCreateDto;
import edu.unimag.dto.ProductoDto;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.dto.ProductoMasVendidoDto;
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

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
    // Esperar a que las categorías se creen
    try {
        Thread.sleep(1000); // Pequeña pausa para asegurar que las categorías estén creadas
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    // Datos de productos de ejemplo
    String[][] productosData = {
        {"Papa criolla lb", "También llamada papa amarilla", "2000", "50", "2025-10-29", "1"},
        {"Leche entera ", "Leche pasteurizada 1 litro", "5500", "3", "2025-06-15", "2"},
        {"Pechuga de pollo", "Pechuga sin hueso ni piel", "12000", "2", "2025-07-20", "3"},
        {"Manzana roja bolsa", "Manzana deliciosa", "4500", "40", "2025-07-10", "4"},
        {"Zanahoria lb", "Zanahoria orgánica", "2500", "35", "2025-06-30", "5"},
        {"Yuca lb", "Yuca fresca", "3000", "20", "2025-08-15", "1"},
        {"Queso fresco lb", "Queso campesino ", "5500", "15", "2025-06-25", "2"},
        {"Carne molida lb", "Carne de res molida", "15000", "18", "2025-07-25", "3"},
        {"Banano unidad", "Banano maduro", "500", "60", "2025-07-30", "4"},
        {"Brócoli lb", "Brócoli fresco", "4000", "22", "2025-06-18", "5"},
        {"Papa pastusa lb", "Papa para cocinar", "3500", "45", "2025-09-10", "1"},
        {"Yogurt litro", "Yogurt natural", "6500", "12", "2025-06-20", "2"},
        {"Pescado paq", "Filete de pescado fresco", "18000", "10", "2025-07-22", "3"},
        {"Naranja bolsa", "Naranja valencia", "3500", "55", "2025-07-05", "4"},
        {"Espinaca lb", "Espinaca fresca", "3200", "30", "2025-06-12", "5"},
        {"Ñame lb", "Ñame fresco", "2800", "25", "2025-08-20", "1"},
        {"Mantequilla paq", "Mantequilla sin sal ", "7000", "8", "2025-07-15", "2"},
        {"Cerdo paq", "Lomo de cerdo", "14000", "12", "2025-06-05", "3"},
        {"Pera bolsa", "Pera williams", "3000", "28", "2025-07-20", "4"},
        {"Cebolla bolsa", "Cebolla cabezona", "2000", "40", "2025-07-25", "5"}
    };

    for (String[] data : productosData) {
        try {
            var categoria = categoriaService.findById(Long.parseLong(data[5]))
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            
            ProductoCreateDto productoCreateDto = new ProductoCreateDto();
            productoCreateDto.setNombre(data[0]);
            productoCreateDto.setDescripcion(data[1]);
            productoCreateDto.setPrecio(Double.parseDouble(data[2]));
            productoCreateDto.setStock(Integer.parseInt(data[3]));
            productoCreateDto.setFecha(LocalDate.parse(data[4]));
            productoCreateDto.setCategoriaId(Long.parseLong(data[5]));
            
            Producto producto = productoMapper.toProducto(productoCreateDto);
            producto.setCategoria(categoria);
            productoService.create(producto);
        } catch (Exception e) {
            System.err.println("Error creando producto: " + data[0] + " - " + e.getMessage());
        }
    }
    }

    @PostMapping
    public ResponseEntity<ProductoDto> createProducto(@Valid @RequestBody ProductoCreateDto productoCreateDto) {
        var categoria = categoriaService.findById(productoCreateDto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoría no encontrada"));
        Producto producto = productoMapper.toProducto(productoCreateDto);
        producto.setCategoria(categoria); 
        Producto createdProducto = productoService.create(producto);
        return new ResponseEntity<>(productoMapper.toProductoDto(createdProducto), HttpStatus.CREATED);
    }

    //Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productoMapper.toDtoList(productos));
    }

    //Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable Long id) {
        return productoService.findById(id)
                .map(productoMapper::toProductoDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    //Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoCreateDto productoCreateDto) {
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

    //Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/mas-vendidos") // <--- La ruta del endpoint
    public ResponseEntity<List<ProductoMasVendidoDto>> getTopSellingProducts() {
        // Puedes pasar un límite aquí, por ejemplo, 5 o 10
        List<ProductoMasVendidoDto> topProducts = productoService.findTopSellingProducts(5); // <-- Obtiene los 5 más vendidos
        return ResponseEntity.ok(topProducts);
    }
}