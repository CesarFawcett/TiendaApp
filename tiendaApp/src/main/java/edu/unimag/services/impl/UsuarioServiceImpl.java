package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unimag.entities.Usuario;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.repositories.UsuarioRepository;
import edu.unimag.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario create(Usuario newUsuario) {
        return usuarioRepository.save(newUsuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario update(Long id, Usuario newUsuario) {
        return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                usuarioExistente.setNombre(newUsuario.getNombre());
                usuarioExistente.setEmail(newUsuario.getEmail());
                usuarioExistente.setPassword(newUsuario.getPassword());
                usuarioExistente.setRol(newUsuario.getRol());
                // No deberías actualizar las ventas aquí normalmente
                return usuarioRepository.save(usuarioExistente);
            })
            .orElseThrow(() -> new EntidadNoEncontradaException("Usuario no encontrado con id: " + id, id));
    }

    @Override
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("Usuario no encontrado con id: " + id, id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public Usuario update(Usuario usuario) throws IllegalArgumentException {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        return this.update(usuario.getId(), usuario);
    }
}
