package edu.unimag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entidad que representa una orden de compra realizada a proveedores.
 * Contiene información sobre la fecha de creación, el estado actual,
 * el proveedor y los productos solicitados con sus cantidades y precios.
 * NOTA:
 * Esta entidad es fundamental para el módulo de abastecimiento y gestión
 * de inventario, permitiendo rastrear las órdenes desde su creación hasta
 * la recepción de los productos.
 */

@Entity
@Table(name = "ordenes_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrdenCompra", description = "Solicitud formal de productos a un proveedor")
public class OrdenCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la orden de compra", example = "1")
    private Long id;

    @Schema(description = "Fecha de emisión de la orden", example = "2025-05-20")
    @Column(nullable = false)
    private LocalDate fecha;

    @Schema(description = "Estado actual de la orden en su ciclo de vida", example = "PENDIENTE")
    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    @Transient
    @Schema(description = "Monto total calculado de la orden (suma de todos los subtotales)", example = "299000")
    public Double getTotal() {
        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getSubtotal())
                .sum();
    }
    
    @Schema(description = "Valor persistente del total calculado", example = "299000")
    @Column(name = "total_calculated")
    private Double totalCalculated;

    @PrePersist
    @PreUpdate
    private void updateTotalCalculated() {
        this.totalCalculated = getTotal();
    }

    // Relación con proveedor 
    @Schema(description = "Proveedor al que se realiza la orden de compra")
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Relación con DetallesOrdenCompra de la compra
    @Schema(description = "Lista de productos solicitados con sus cantidades y precios")
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesOrdenCompra> detalles;

    //Relacion con HistorialEstadoOrden
    @Schema(description = "Historial completo de cambios de estado de esta orden")
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<HistorialEstadoOrden> historialEstados = new ArrayList<>();

    /**
     * Método para registrar un cambio de estado en la orden y crear el registro histórico correspondiente
     * NOTA:
     * @param nuevoEstado El nuevo estado de la orden
     * @param usuario Usuario que realiza el cambio
     * @param observacion Comentario o razón del cambio
     */
    public void cambiarEstado(EstadoOrden nuevoEstado, Usuario usuario, String observacion) {
        EstadoOrden estadoAnterior = this.estado;
        this.estado = nuevoEstado;
        
        HistorialEstadoOrden historial = new HistorialEstadoOrden();
        historial.setOrdenCompra(this);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(nuevoEstado);
        historial.setFechaCambio(LocalDateTime.now());
        historial.setUsuario(usuario);
        historial.setObservacion(observacion);
        
        this.historialEstados.add(historial);
    }

    /**
     * Añade un nuevo detalle a la orden de compra y establece la relación bidireccional
     * NOTA:
     * @param detalle Detalle de producto a añadir a la orden
     */
    public void addDetalle(DetallesOrdenCompra detalle) {
        detalles.add(detalle);
        detalle.setOrdenCompra(this);
    }
    
    /**
     * Elimina un detalle de la orden de compra y rompe la relación bidireccional
     * NOTA:
     * @param detalle Detalle de producto a eliminar de la orden
     */
    public void removeDetalle(DetallesOrdenCompra detalle) {
        detalles.remove(detalle);
        detalle.setOrdenCompra(null);
    }
    
}
