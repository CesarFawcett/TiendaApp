package edu.unimag.dto;

import edu.unimag.entities.Rol;
import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    //private String contraseña;
    // No incluimos la contraseña por seguridad
}
