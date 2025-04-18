package edu.unimag.entities;

/**
 * Enumeración que define los posibles estados de una orden de compra a proveedores.
 * 
 * Los estados representan el ciclo de vida completo de una orden desde su creación
 * hasta su finalización o cancelación:
 * NOTA:
 * - PENDIENTE: La orden ha sido creada pero aún no ha sido enviada al proveedor
 * - ENVIADA: La orden ha sido enviada al proveedor y se está esperando su respuesta
 * - RECIBIDA: Los productos de la orden han sido recibidos satisfactoriamente
 * - CANCELADA: La orden ha sido cancelada y no se procesará
 */

public enum EstadoOrden {
    /**
     * La orden está creada pero aún no se ha enviado al proveedor
     */
    PENDIENTE,
    
    /**
     * La orden ha sido enviada al proveedor y está en espera de recepción
     */
    ENVIADA,
    
    /**
     * Los productos de la orden han sido recibidos satisfactoriamente
     */
    RECIBIDA,
    
    /**
     * La orden ha sido cancelada y no será procesada
     */
    CANCELADA
}
