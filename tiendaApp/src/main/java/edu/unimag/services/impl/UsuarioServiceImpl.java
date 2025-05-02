package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import edu.unimag.entities.Usuario;
import edu.unimag.repositories.UsuarioRepository;
import edu.unimag.services.UsuarioService;

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
         Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
    if (existingUsuario.isPresent()) {
        Usuario usuarioToUpdate = existingUsuario.get();
        usuarioToUpdate.setNombre(newUsuario.getNombre());
        usuarioToUpdate.setEmail(newUsuario.getEmail());
        usuarioToUpdate.setContraseña(newUsuario.getContraseña());
        usuarioToUpdate.setRol(newUsuario.getRol());
        usuarioToUpdate.setVentas(newUsuario.getVentas());
        return usuarioRepository.save(usuarioToUpdate);
    } else {
        return null;
    }
         
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id); 
    }
    
}
