package edu.unimag.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa a los proveedores de productos para el sistema.
 * Contiene información detallada del proveedor, incluyendo datos de contacto,
 * dirección física y el historial de órdenes de compra realizadas.
 * NOTA:
 * Esta entidad es clave para el módulo de abastecimiento y la gestión de
 * relaciones con proveedores.
 */

@Entity
@Table(name = "proveedores")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Proveedor", description = "Empresa o persona que suministra productos al sistema")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único autogenerado del proveedor", example = "1")
    private Long id;

    @Schema(description = "Nombre comercial o razón social del proveedor", example = "Distribuidora Nacional S.A.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Nombre completo de la persona de contacto principal", example = "Juan Pérez Rodríguez")
    @Column(nullable = false)
    private String contacto;

    @Schema(description = "Número telefónico de contacto directo", example = "+57 3101234567")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$", message = "Teléfono inválido")
    @Column(nullable = false)
    private String telefono;

    @Schema(description = "Correo electrónico para comunicaciones y envío de órdenes", example = "pedidos@distribuidora.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Debe ser un email válido")
    @Column(nullable = false)
    private String email;

    @Schema(description = "Dirección física completa del proveedor", example = "Calle 123 #45-67, Zona Industrial, Bogotá")
    @Column(nullable = false)
    private String direccion;

    // Relación con órdenes de compra
    @Schema(description = "Historial de órdenes de compra realizadas a este proveedor")
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdenCompra> ordenesCompra;

}
