package edu.unimag.dto;

import edu.unimag.entities.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public VentaDto toVentaDto(Venta venta) {
        return new VentaDto(
                venta.getId(),
                venta.getFecha(),
                venta.getCliente().getId(),
                venta.getUsuario().getId(),
                venta.getTotal()
        );
    }

    public Venta toVenta(VentaCreateDto ventaCreateDto) {
        Venta venta = new Venta();
        venta.setFecha(ventaCreateDto.getFecha());
        return venta;
    }

    public List<VentaDto> toDtoList(List<Venta> ventas) {
        return ventas.stream()
                .map(this::toVentaDto)
                .collect(Collectors.toList());
    }
}
