package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Usuario;
import edu.unimag.exception.EntidadNoEncontradaException;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario create(Usuario newUsuario);
    Optional<Usuario> findById(Long id);
    Usuario update(Long id, Usuario newUsuario);
    boolean existsById(Long id);
    void delete(Long id) throws EntidadNoEncontradaException;
    Usuario update(Usuario usuario) throws IllegalArgumentException;
}
