package edu.unimag.dto;

import edu.unimag.entities.DetallesVenta;
import edu.unimag.entities.Producto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DetallesVentaMapper {

    public DetallesVentaDto toDetallesVentaDto(DetallesVenta detallesVenta) {
        DetallesVentaDto dto = new DetallesVentaDto();
        dto.setId(detallesVenta.getId());
        dto.setCantidad(detallesVenta.getCantidad());
        dto.setPrecioUnitario(detallesVenta.getPrecioUnitario());

        Producto producto = detallesVenta.getProducto();
        if (producto != null) {
            dto.setProductoId(producto.getId());
            dto.setProductoNombre(producto.getNombre());
        }

        dto.setSubtotal(detallesVenta.getCantidad() * detallesVenta.getPrecioUnitario());
        return dto;
    }

    public List<DetallesVentaDto> toDtoList(List<DetallesVenta> detallesList) {
        List<DetallesVentaDto> dtoList = new ArrayList<>();
        for (DetallesVenta d : detallesList) {
            dtoList.add(toDetallesVentaDto(d));
        }
        return dtoList;
    }
}
