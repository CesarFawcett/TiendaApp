package edu.unimag.services;

import java.util.List;
import java.util.Optional;
import edu.unimag.entities.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario create(Usuario newUsuario);
    Optional<Usuario> finById(Long id);
    Usuario update(Long id, Usuario newUsuario);
    void delete(Long id);
}
