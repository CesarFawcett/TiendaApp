package edu.unimag.dto;

import edu.unimag.entities.OrdenCompra;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdenCompraMapper {

    public OrdenCompraDto toOrdenCompraDto(OrdenCompra ordenCompra) {
        OrdenCompraDto ordenCompraDto = new OrdenCompraDto();
        ordenCompraDto.setId(ordenCompra.getId());
        ordenCompraDto.setFecha(ordenCompra.getFecha());
        ordenCompraDto.setEstado(ordenCompra.getEstado());
        ordenCompraDto.setProveedorId(ordenCompra.getProveedor().getId());  // Suponiendo que solo necesitas el ID del proveedor
        ordenCompraDto.setTotalCalculated(ordenCompra.getTotalCalculated());
        //  No mapeamos 'detalles' ni 'historialEstados' en este DTO básico
        return ordenCompraDto;
    }

    public OrdenCompra toOrdenCompra(OrdenCompraCreateDto ordenCompraCreateDto) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setFecha(ordenCompraCreateDto.getFecha());
        ordenCompra.setEstado(ordenCompraCreateDto.getEstado());
        //  Proveedor y Detalles se manejan en el servicio/controlador
        return ordenCompra;
    }

    public List<OrdenCompraDto> toDtoList(List<OrdenCompra> ordenesCompra) {
        return ordenesCompra.stream()
                .map(this::toOrdenCompraDto)
                .collect(Collectors.toList());
    }
}
