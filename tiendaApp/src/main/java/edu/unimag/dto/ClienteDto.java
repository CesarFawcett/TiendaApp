package edu.unimag.dto;

import lombok.Data;

@Data
public class ClienteDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
}
