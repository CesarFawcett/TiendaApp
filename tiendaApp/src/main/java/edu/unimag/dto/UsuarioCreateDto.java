package edu.unimag.dto;

import edu.unimag.entities.Rol;
import lombok.Data;

@Data
public class UsuarioCreateDto {
    private String nombre;
    private String email;
    private String contraseña;
    private Rol rol;
}