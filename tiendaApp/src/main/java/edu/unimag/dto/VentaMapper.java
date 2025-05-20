package edu.unimag.dto;

import edu.unimag.entities.Venta;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public VentaDto toVentaDto(Venta venta) {
        VentaDto ventaDto = new VentaDto();
        ventaDto.setId(venta.getId());
        ventaDto.setFecha(venta.getFecha());
        ventaDto.setClienteId(null); //   se maneja en el servicio/controlador
        ventaDto.setUsuarioId(null); //  se maneja en el servicio/controlador
        ventaDto.setTotal(venta.getTotal());

        return ventaDto;
    }

    public Venta toVenta(VentaCreateDto ventaCreateDto) {
        Venta venta = new Venta();
        venta.setFecha(ventaCreateDto.getFecha());
        venta.setCliente(null);//  se maneja en el servicio/controlador
        venta.setUsuario(null);//  se maneja en el servicio/controlador
        venta.setDetalles(null);//  se maneja en el servicio/controlador
        return venta;
    }

    public List<VentaDto> toDtoList(List<Venta> ventas) {
    System.out.println("Mapeando ventas: " + ventas.size()); // Debug 5
    return ventas.stream()
        .map(this::toVentaDto)
        .peek(dto -> System.out.println("DTO mapeado: " + dto)) // Debug 6
        .collect(Collectors.toList());
}
}
