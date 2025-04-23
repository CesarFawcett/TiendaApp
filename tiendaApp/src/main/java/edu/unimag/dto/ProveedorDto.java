package edu.unimag.dto;

import lombok.Data;

@Data
public class ProveedorDto {
    private Long id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
}