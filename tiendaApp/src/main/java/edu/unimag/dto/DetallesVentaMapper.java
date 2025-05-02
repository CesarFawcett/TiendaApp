package edu.unimag.dto;

import edu.unimag.entities.DetallesVenta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetallesVentaMapper {

    public DetallesVentaDto toDetallesVentaDto(DetallesVenta detalle) {
        return new DetallesVentaDto(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getProducto().getId(),
                detalle.getSubtotal()
        );
    }

    public DetallesVenta toDetallesVenta(DetallesVentaCreateDto detalleCreateDto) {
        DetallesVenta detalle = new DetallesVenta();
        detalle.setCantidad(detalleCreateDto.getCantidad());
        detalle.setPrecioUnitario(detalleCreateDto.getPrecioUnitario());
        return detalle;
    }

    public List<DetallesVentaDto> toDtoList(List<DetallesVenta> detalles) {
        return detalles.stream()
                .map(this::toDetallesVentaDto)
                .collect(Collectors.toList());
    }
}
