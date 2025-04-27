package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
