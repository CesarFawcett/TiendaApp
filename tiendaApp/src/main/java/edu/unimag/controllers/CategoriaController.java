package edu.unimag.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.unimag.entities.Categoria;
import edu.unimag.repositories.CategoriaRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    
    @Autowired
private CategoriaRepository categoriaRepository;

@PostConstruct 
public void crearCategoriaDePrueba() {
    if (!categoriaRepository.existsByNombre("Granos")) {
        Categoria categoria = new Categoria();
        categoria.setNombre("Granos");
        categoria.setDescripcion("Productos como arroz, frijoles, lentejas y similares");
        categoriaRepository.save(categoria);
        System.out.println("✅ Categoría de prueba creada");
    }
}
}
