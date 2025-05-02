package edu.unimag.dto;

import edu.unimag.entities.Auditoria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuditoriaMapper {

    public AuditoriaDto toAuditoriaDto(Auditoria auditoria) {
        return new AuditoriaDto(
                auditoria.getId(),
                auditoria.getAccion(),
                auditoria.getEntidadAfectada(),
                auditoria.getIdEntidad(),
                auditoria.getFecha(),
                auditoria.getDetalle()
        );
    }

    public Auditoria toAuditoria(AuditoriaCreateDto auditoriaCreateDto) {
        Auditoria auditoria = new Auditoria();
        auditoria.setAccion(auditoriaCreateDto.getAccion());
        auditoria.setEntidadAfectada(auditoriaCreateDto.getEntidadAfectada());
        auditoria.setIdEntidad(auditoriaCreateDto.getIdEntidad());
        auditoria.setFecha(auditoriaCreateDto.getFecha());
        auditoria.setDetalle(auditoriaCreateDto.getDetalle());
        return auditoria;
    }

    public List<AuditoriaDto> toDtoList(List<Auditoria> auditorias) {
        return auditorias.stream()
                .map(this::toAuditoriaDto)
                .collect(Collectors.toList());
    }
}
