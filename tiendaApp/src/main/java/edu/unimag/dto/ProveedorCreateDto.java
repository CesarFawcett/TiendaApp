package edu.unimag.dto;

import lombok.Data;

@Data
public class ProveedorCreateDto {
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
}