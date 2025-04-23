package edu.unimag.dto;

import lombok.Data;
import java.util.List;

@Data
public class VentaCreateDto {
    private Long clienteId;
    private List<DetallesVentaCreateDto> detalles;
    //private LocalDateTime fecha;
    // private Usuario usuario;
    // La fecha se asignará automáticamente en el servicio
    // El usuario se tomará del contexto de seguridad
}
