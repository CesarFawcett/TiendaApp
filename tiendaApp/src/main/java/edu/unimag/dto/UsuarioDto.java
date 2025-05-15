package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import edu.unimag.entities.Rol;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de Usuario")
public class UsuarioDto {
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Carlos Pérez")
    private String nombre;

    @Schema(description = "Email del usuario", example = "carlos@example.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "ADMINISTRADOR")
    private Rol rol = Rol.USUARIO;
}
