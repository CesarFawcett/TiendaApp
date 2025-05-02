package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import edu.unimag.entities.Rol;

@Data
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo Usuario")
public class UsuarioCreateDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del usuario", example = "Carlos Pérez")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Debe ser un email válido")
    @Schema(description = "Email del usuario", example = "carlos@example.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String contraseña;

    @NotNull(message = "El rol es obligatorio")
    @Schema(description = "Rol del usuario", example = "ADMINISTRADOR")
    private Rol rol;
}

