package edu.unimag.dto;

import edu.unimag.entities.DetallesOrdenCompra;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetallesOrdenCompraMapper {

    public DetallesOrdenCompraDto toDetallesOrdenCompraDto(DetallesOrdenCompra detalle) {
        return new DetallesOrdenCompraDto(
                detalle.getId(),
                detalle.getProducto().getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }

    public DetallesOrdenCompra toDetallesOrdenCompra(DetallesOrdenCompraCreateDto detalleCreateDto) {
        DetallesOrdenCompra detalle = new DetallesOrdenCompra();
        detalle.setCantidad(detalleCreateDto.getCantidad());
        detalle.setPrecioUnitario(detalleCreateDto.getPrecioUnitario());
        return detalle;
    }

    public List<DetallesOrdenCompraDto> toDtoList(List<DetallesOrdenCompra> detalles) {
        return detalles.stream()
                .map(this::toDetallesOrdenCompraDto)
                .collect(Collectors.toList());
    }
}
