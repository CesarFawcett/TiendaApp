package edu.unimag.services;

import java.util.List;
import java.util.Optional;

import edu.unimag.entities.Categoria;

public interface CategoriaService {
    Categoria create (Categoria newCategoria);
    List<Categoria> findAll();
    Optional<Categoria> findById(Long id);
    Categoria update(Long id);
    void delete(Long id);
}
