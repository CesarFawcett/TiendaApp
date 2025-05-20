package edu.unimag.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Schema(description = "DTO para la creación de un nuevo Cliente")
public class ClienteCreateDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Debe ser un email válido")
    @Schema(description = "Email del cliente", example = "juan@example.com")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Teléfono inválido")
    @Schema(description = "Teléfono del cliente", example = "+15551234567")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección del cliente", example = "Calle 123, Ciudad")
    private String direccion;
}

