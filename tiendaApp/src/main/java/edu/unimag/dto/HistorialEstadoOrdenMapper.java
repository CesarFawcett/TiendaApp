package edu.unimag.dto;

import edu.unimag.entities.HistorialEstadoOrden;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistorialEstadoOrdenMapper {

    public HistorialEstadoOrdenDto toHistorialEstadoOrdenDto(HistorialEstadoOrden historial) {
        return new HistorialEstadoOrdenDto(
                historial.getId(),
                historial.getOrdenCompra().getId(),
                historial.getEstadoAnterior(),
                historial.getEstadoNuevo(),
                historial.getFechaCambio(),
                historial.getUsuario().getId(),
                historial.getObservacion()
        );
    }

    public HistorialEstadoOrden toHistorialEstadoOrden(HistorialEstadoOrdenCreateDto historialCreateDto) {
        HistorialEstadoOrden historial = new HistorialEstadoOrden();
        historial.setEstadoAnterior(historialCreateDto.getEstadoAnterior());
        historial.setEstadoNuevo(historialCreateDto.getEstadoNuevo());
        historial.setFechaCambio(historialCreateDto.getFechaCambio());
        historial.setObservacion(historialCreateDto.getObservacion());
        return historial;
    }

    public List<HistorialEstadoOrdenDto> toDtoList(List<HistorialEstadoOrden> historiales) {
        return historiales.stream()
                .map(this::toHistorialEstadoOrdenDto)
                .collect(Collectors.toList());
    }
}
