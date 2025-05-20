package edu.unimag.dto;

import edu.unimag.entities.Venta;
import edu.unimag.repositories.ClienteRepository;
import edu.unimag.repositories.UsuarioRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {
     private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaMapper(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public VentaDto toVentaDto(Venta venta) {
        VentaDto ventaDto = new VentaDto();
        ventaDto.setId(venta.getId());
        ventaDto.setFecha(venta.getFecha());
        ventaDto.setClienteId(venta.getCliente() != null ? venta.getCliente().getId() : null);
        ventaDto.setUsuarioId(venta.getUsuario() != null ? venta.getUsuario().getId() : null);
        ventaDto.setTotal(venta.getTotal());
        return ventaDto;
    }

    public Venta toVenta(VentaCreateDto ventaCreateDto) {
        Venta venta = new Venta();
        venta.setFecha(ventaCreateDto.getFecha());
        
        // Buscar y establecer cliente
        clienteRepository.findById(ventaCreateDto.getClienteId())
            .ifPresent(venta::setCliente);
            
        // Buscar y establecer usuario
        usuarioRepository.findById(ventaCreateDto.getUsuarioId())
            .ifPresent(venta::setUsuario);
            
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
