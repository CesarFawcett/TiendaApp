package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Categoria;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria create (Categoria newCategoria);
    Optional<Categoria> findById(Long id);
    Categoria update(Long id,Categoria newCategoria);
    void delete(Long id);
}
