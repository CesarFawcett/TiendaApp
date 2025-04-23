package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unimag.entities.Categoria;
import edu.unimag.repositories.CategoriaRepository;
import edu.unimag.services.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {
 
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria create(Categoria newCategoria) {
        return categoriaRepository.save(newCategoria);
       }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
      }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
      }

    @Override
    public Categoria update(Long id) {
        Optional<Categoria> existingCategoria = categoriaRepository.findById(id);
          if (existingCategoria.isPresent()) {
              Categoria categoriaToUpdate = existingCategoria.get();
              categoriaToUpdate.setNombre("Nuevo nombre");
              return categoriaRepository.save(categoriaToUpdate);
          } else {
              return null;  
          }
    }

    @Override
    public void delete(Long id) {
      categoriaRepository.deleteById(id);
    }
}