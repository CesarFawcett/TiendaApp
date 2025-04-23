package edu.unimag.dto;

import lombok.Data;

@Data
public class DetallesOrdenCompraCreateDto {
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
}