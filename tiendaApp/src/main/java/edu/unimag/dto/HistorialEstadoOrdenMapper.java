package edu.unimag.dto;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import edu.unimag.entities.HistorialEstadoOrden;
import edu.unimag.entities.Usuario;

@Component
public class HistorialEstadoOrdenMapper {

    public HistorialEstadoOrdenDto toHistorialEstadoOrdenDto(HistorialEstadoOrden historialEstadoOrden) {
        HistorialEstadoOrdenDto dto = new HistorialEstadoOrdenDto();
        dto.setId(historialEstadoOrden.getId());
        dto.setOrdenCompraId(historialEstadoOrden.getOrdenCompraId());
        dto.setEstadoAnterior(historialEstadoOrden.getEstadoAnterior());
        dto.setEstadoNuevo(historialEstadoOrden.getEstadoNuevo());
        dto.setFechaCambio(historialEstadoOrden.getFechaCambio());
        dto.setObservacion(historialEstadoOrden.getObservacion());
        dto.setUsuarioId(historialEstadoOrden.getUsuario().getId());
        dto.setUsuarioNombre(historialEstadoOrden.getUsuario().getNombre());
        return dto;
    }

    public HistorialEstadoOrden toHistorialEstadoOrden(HistorialEstadoOrdenCreateDto historialEstadoOrdenCreateDto, Usuario usuario) {
        HistorialEstadoOrden historialEstadoOrden = new HistorialEstadoOrden();
        historialEstadoOrden.setEstadoNuevo(historialEstadoOrdenCreateDto.getEstadoNuevo());
        historialEstadoOrden.setObservacion(historialEstadoOrdenCreateDto.getObservacion());
        // The state and the date will be automatically filled
        historialEstadoOrden.setFechaCambio(LocalDateTime.now());
        historialEstadoOrden.setUsuario(usuario);
        return historialEstadoOrden;
    }
}

