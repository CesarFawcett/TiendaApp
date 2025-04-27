package edu.unimag.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoCreateDto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private LocalDate fecha;
    private Long categoriaId;

}
