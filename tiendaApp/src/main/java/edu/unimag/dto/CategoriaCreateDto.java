package edu.unimag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoriaCreateDto {
    private String nombre;
    private String descripcion;
}