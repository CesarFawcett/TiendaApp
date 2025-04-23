package edu.unimag.controllers;

import org.springframework.web.bind.annotation.*;
import edu.unimag.entities.Categoria;
import edu.unimag.services.CategoriaService;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){

        this.categoriaService = categoriaService;
    }

    @PostConstruct
    public void initSampleCategoria(){
       
        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Tubérculo");
        categoria1.setDescripcion("Carbohidratos complejos");
        categoriaService.create(categoria1);
    }
}
