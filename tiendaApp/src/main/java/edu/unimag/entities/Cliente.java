package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa a los clientes que realizan compras en el sistema.
 * Almacena la información de contacto y dirección del cliente, así como
 * el historial de ventas asociadas a cada cliente.
 * NOTA:
 * Esta entidad es fundamental para el módulo de ventas y la gestión de la
 * relación con los clientes (CRM).
 */

@Entity
@Table(name = "clientes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Cliente", description = "Persona o empresa que realiza compras en el sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del cliente o razón social", example = "Andres Leon")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Correo electrónico de contacto para facturación y notificaciones", example = "usuario@dominio.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Debe ser un email válido")
    @Column(nullable = false)
    private String email;

    @Schema(description = "Teléfono de contacto principal", example = "+57 3101234567")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Teléfono inválido")
    @Column(nullable = false)
    private String telefono;

    @Schema(description = "Dirección física para entregas y facturación", example = "Calle 123 #45-67, Bogotá")
    @Column(nullable = false)
    private String direccion;

    // Relacion con Venta
    @Schema(description = "Historial de ventas realizadas al cliente")
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();

    public Cliente(Long id) {
    this.id = id;
    }
}
