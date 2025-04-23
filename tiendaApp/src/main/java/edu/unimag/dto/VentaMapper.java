package edu.unimag.dto;

import edu.unimag.entities.Venta;
import edu.unimag.entities.Cliente;
import edu.unimag.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class VentaMapper {

    @Autowired
    private DetallesVentaMapper detallesVentaMapper;

    public VentaDto toVentaDto(Venta venta) {
        VentaDto dto = new VentaDto();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());

        Cliente cliente = venta.getCliente();
        if (cliente != null) {
            dto.setClienteId(cliente.getId());
            dto.setClienteNombre(cliente.getNombre());
        }

        Usuario usuario = venta.getUsuario();
        if (usuario != null) {
            dto.setUsuarioId(usuario.getId());
            dto.setUsuarioNombre(usuario.getNombre());
        }

        dto.setDetalles(detallesVentaMapper.toDtoList(venta.getDetalles()));
        dto.setTotal(venta.getTotal()); // Asumiendo que hay un método calcularTotal()
        return dto;
    }

    public List<VentaDto> toDtoList(List<Venta> ventas) {
        List<VentaDto> dtoList = new ArrayList<>();
        for (Venta venta : ventas) {
            dtoList.add(toVentaDto(venta));
        }
        return dtoList;
    }
}
