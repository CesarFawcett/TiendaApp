package edu.unimag.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Auditoria;
import edu.unimag.entities.Usuario;

@Component
public class AuditoriaMapper {

    public AuditoriaDto toAuditoriaDto(Auditoria auditoria) {
        AuditoriaDto auditoriaDto = new AuditoriaDto();
        auditoriaDto.setId(auditoria.getId());
        auditoriaDto.setAccion(auditoria.getAccion());
        auditoriaDto.setEntidadAfectada(auditoria.getEntidadAfectada());
        auditoriaDto.setIdEntidad(auditoria.getIdEntidad());
        auditoriaDto.setFecha(auditoria.getFecha());
        auditoriaDto.setDetalle(auditoria.getDetalle());
        auditoriaDto.setUsuarioId(auditoria.getUsuario().getId());
        auditoriaDto.setUsuarioNombre(auditoria.getUsuario().getNombre());
        return auditoriaDto;
    }

    public Auditoria toAuditoria(AuditoriaCreateDto auditoriaCreateDto, Usuario usuario) {
        Auditoria auditoria = new Auditoria();
        auditoria.setAccion(auditoriaCreateDto.getAccion());
        auditoria.setEntidadAfectada(auditoriaCreateDto.getEntidadAfectada());
        auditoria.setIdEntidad(auditoriaCreateDto.getIdEntidad());
        auditoria.setFecha(auditoriaCreateDto.getFecha() != null ? auditoriaCreateDto.getFecha() : LocalDateTime.now());
        auditoria.setDetalle(auditoriaCreateDto.getDetalle());
        auditoria.setUsuario(usuario);
        return auditoria;
    }
}

