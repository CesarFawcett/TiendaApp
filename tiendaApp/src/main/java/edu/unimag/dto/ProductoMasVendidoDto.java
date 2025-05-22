package edu.unimag.dto;


public class ProductoMasVendidoDto {
    private Long idProducto;
    private String nombreProducto;
    private Long cantidadVendida;

    // ESTE CONSTRUCTOR ES CLAVE: Debe coincidir con los tipos y orden de la JPQL
    public ProductoMasVendidoDto(Long idProducto, String nombreProducto, Long cantidadVendida) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
    }

    // Si no usas Lombok, necesitas tus getters manualmente:
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Long cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
}