package edu.unimag.controllers;

import edu.unimag.entities.Usuario;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.dto.UsuarioDto;
import edu.unimag.dto.UsuarioCreateDto;
import edu.unimag.dto.UsuarioMapper;
import edu.unimag.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    //Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarioMapper.toDtoList(usuarios));
    }

    //Obtener los usuarios por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
            return usuarioService.findById(id)
                .map(usuarioMapper::toUsuarioDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    //Crear nueva usuario
    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioCreateDto);
        Usuario createdUsuario = usuarioService.create(usuario);
        return new ResponseEntity<>(usuarioMapper.toUsuarioDto(createdUsuario), HttpStatus.CREATED); 
    
    }

    //Actualizar usuario
   @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUser(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDto usuarioCreateDto) {
    try {
        if (!usuarioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioMapper.toUsuario(usuarioCreateDto);
        usuario.setId(id); // Asegurar que el ID se mantenga
        Usuario updatedUsuario = usuarioService.update(usuario);
        return ResponseEntity.ok(usuarioMapper.toUsuarioDto(updatedUsuario));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
   }

    //Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
    try {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    } catch (EntidadNoEncontradaException e) {
        return ResponseEntity.notFound().build();
    }
}
}
