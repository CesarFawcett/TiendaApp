package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos de Cliente")
public class ClienteDto {
    @Schema(description = "ID único del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Email del cliente", example = "juan@example.com")
    private String email;

    @Schema(description = "Teléfono del cliente", example = "+15551234567")
    private String telefono;

    @Schema(description = "Dirección del cliente", example = "Calle 123, Ciudad")
    private String direccion;
}
