package edu.unimag.dto;

import edu.unimag.entities.Usuario;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

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
        usuario.setPassword(usuarioCreateDto.getPassword());
        usuario.setRol(usuarioCreateDto.getRol());
        return usuario;
    }

    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toUsuarioDto)
                .collect(Collectors.toList());
    }
}