package edu.unimag.controllers;

import edu.unimag.dto.CategoriaDto;
import edu.unimag.dto.CategoriaCreateDto;
import edu.unimag.dto.CategoriaMapper;
import edu.unimag.entities.Categoria;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @PostConstruct
    public void initSampleCategoria() {
        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Tubérculo");
        categoria1.setDescripcion("Carbohidratos complejos");
        categoriaService.create(categoria1);
    }

    //Crear nueva categoría
    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody CategoriaCreateDto categoriaCreateDto) {
        Categoria categoria = categoriaMapper.toCategoria(categoriaCreateDto);
        Categoria createdCategoria = categoriaService.create(categoria);
        CategoriaDto categoriaDto = categoriaMapper.toCategoriaDto(createdCategoria);
        return new ResponseEntity<>(categoriaDto, HttpStatus.CREATED); 
    }

    //Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDto> categoriaDtos = categoriaMapper.toDtoList(categorias);
        return ResponseEntity.ok(categoriaDtos);
    }

    //Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoriaMapper::toCategoriaDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    //Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long id, @RequestBody CategoriaCreateDto categoriaCreateDto) {
        try {
            Categoria categoria = categoriaMapper.toCategoria(categoriaCreateDto);
            Categoria updatedCategoria = categoriaService.update(id, categoria);
            return ResponseEntity.ok(categoriaMapper.toCategoriaDto(updatedCategoria));
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); 
        }
    }

    //Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}