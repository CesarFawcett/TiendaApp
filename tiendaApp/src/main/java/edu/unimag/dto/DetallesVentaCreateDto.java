package edu.unimag.dto;

import lombok.Data;

@Data
public class DetallesVentaCreateDto {
    private Integer cantidad;
    private Long productoId;
    //private Double precioUnitario;
    // El precio unitario se tomará del producto al momento de la creación
}