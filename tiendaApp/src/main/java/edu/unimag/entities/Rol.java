package edu.unimag.entities;

/**
 * Enumeración que define los distintos roles que pueden tener los usuarios en el sistema.
 *
 * Los roles determinan el nivel de acceso y las funcionalidades disponibles para cada
 * usuario dentro de los diferentes módulos del sistema:
 * NOTA:
 * - ADMINISTRADOR: Tiene acceso completo a todas las funcionalidades, incluyendo configuración, usuarios y reportes.
 * - VENDEDOR: Accede a funcionalidades relacionadas con ventas, clientes y generación de facturas.
 * - ALMACÉN: Tiene acceso a módulos de inventario, recepción de productos y gestión de stock.
 */
public enum Rol {
    
    /**
     * Tiene acceso total a todas las secciones y operaciones del sistema
     */
    ADMINISTRADOR,
    
    /**
     * Encargado de realizar ventas y atender a los clientes
     */
    VENDEDOR,
    
    /**
     * Responsable de las operaciones en bodega e inventario
     */
    ALMACEN,

    USUARIO
}
