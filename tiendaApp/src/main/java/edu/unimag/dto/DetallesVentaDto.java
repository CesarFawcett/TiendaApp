package edu.unimag.dto;

import lombok.Data;

@Data
public class DetallesVentaDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Long productoId;
    private String productoNombre;
    
    // Campo calculado
    private Double subtotal;
}