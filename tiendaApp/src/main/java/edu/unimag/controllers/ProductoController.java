package edu.unimag.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.unimag.dto.ProductoDto;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.entities.Producto;
import edu.unimag.services.ProductoService;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapper productoMaper){
        this.productoService = productoService;
        this.productoMapper = productoMaper;
    }

    @PostConstruct
    public void initSampleProducto(){
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Producto producto1 = new Producto();
        producto1.setNombre("papa criolla");
        producto1.setDescripcion("tambien llamada papa amarilla");
        producto1.setPrecio(20.5);
        producto1.setFecha(LocalDate.parse("2025-04-29 11:30", dataFormatter));
        producto1.setStock(20);
        
        productoService.create(producto1);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductoDto>>findAll(){
        List<Producto> productos = productoService.findAll();
        List<ProductoDto> productoDtos = productos.stream()
                      .map(productoMapper::toProductoDto)
                      .collect(Collectors.toList());
                    return ResponseEntity.ok(productoDtos);
    }
}
