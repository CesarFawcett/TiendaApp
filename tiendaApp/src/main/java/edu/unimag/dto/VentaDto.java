package edu.unimag.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDto {
    private Long id;
    private LocalDateTime fecha;
    private Long clienteId;
    private String clienteNombre;
    private Long usuarioId;
    private String usuarioNombre;
    private List<DetallesVentaDto> detalles;
    private Double total;
}