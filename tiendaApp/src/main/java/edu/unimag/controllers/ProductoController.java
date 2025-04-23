package edu.unimag.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.*;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.entities.Categoria;
import edu.unimag.entities.Producto;
import edu.unimag.services.CategoriaService;
import edu.unimag.services.ProductoService;
import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    private final ProductoService productoService;
    private final ProductoMapper productoMapper;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, ProductoMapper productoMaper, CategoriaService categoriaService){
        this.productoService = productoService;
        this.productoMapper = productoMaper;
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
    producto1.setDescripcion("tambien llamada papa amarilla");
    producto1.setPrecio(20.5);
    producto1.setFecha(LocalDate.parse("2025-04-29 11:30", dataFormatter));
    producto1.setStock(20);
    producto1.setCategoria(categoria1);

    productoService.create(producto1);
}
    

}
