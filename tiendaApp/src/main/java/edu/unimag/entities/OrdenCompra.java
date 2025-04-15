package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 
 * 
 */

@Entity
@Table(name = "ordenes_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrdenCompra", description = "Entidad que representa una orden de compra a proveedores")
public class OrdenCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la orden", example = "1")
    private Long id;

    @Schema(description = "Fecha de creación de la orden", example = "2025-05-20")
    @Column(nullable = false)
    private LocalDate fecha;

    @Schema(description = "Estado de la orden", example = "PENDIENTE")
    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    @Schema(description = "Total de la compra", example = "299000")
    @Column(nullable = false)
    private BigDecimal total;

    // Relación con proveedor 
    @Schema(description = "Proveedor asociado a la orden")
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Relación con DetallesOrdenCompra de la compra
    @Schema(description = "Lista de detalles de productos en la orden")
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesOrdenCompra> detalles;

}
