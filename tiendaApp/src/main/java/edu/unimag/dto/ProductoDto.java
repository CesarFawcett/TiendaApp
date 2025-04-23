package edu.unimag.dto;

import java.time.LocalDate;
import edu.unimag.entities.Categoria;
import lombok.Data;

@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private LocalDate fecha;
    private Categoria categoria;
 
}

