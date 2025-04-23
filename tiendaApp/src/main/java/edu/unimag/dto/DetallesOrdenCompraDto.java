package edu.unimag.dto;

import lombok.Data;

@Data
public class DetallesOrdenCompraDto {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}