package edu.unimag.dto;

import lombok.Data;

@Data
public class ClienteCreateDto {
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
}