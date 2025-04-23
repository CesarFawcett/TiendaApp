package edu.unimag.dto;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {
    
    public UsuarioDto toUsuarioDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setRol(usuario.getRol());
        return usuarioDto;
    }
    
    public Usuario toUsuario(UsuarioCreateDto usuarioCreateDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioCreateDto.getNombre());
        usuario.setEmail(usuarioCreateDto.getEmail());
        usuario.setContraseña(usuarioCreateDto.getContraseña()); // Nota: Esto debería estar encriptado en un caso real
        usuario.setRol(usuarioCreateDto.getRol());
        return usuario;
    }
    
    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        List<UsuarioDto> usuarioDtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDtos.add(toUsuarioDto(usuario));
        }
        return usuarioDtos;
    }
}