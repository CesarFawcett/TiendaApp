package edu.unimag.controllers;

import edu.unimag.entities.Auditoria;
import edu.unimag.dto.AuditoriaDto;
import edu.unimag.dto.AuditoriaMapper;
import edu.unimag.services.AuditoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final AuditoriaMapper auditoriaMapper;

    public AuditoriaController(AuditoriaService auditoriaService, AuditoriaMapper auditoriaMapper) {
        this.auditoriaService = auditoriaService;
        this.auditoriaMapper = auditoriaMapper;
    }

    //Obtener todas las auditorias
    @GetMapping
    public ResponseEntity<List<AuditoriaDto>> findAll() {
        List<Auditoria> auditorias = auditoriaService.findAll();
        List<AuditoriaDto> auditoriaDtos = auditoriaMapper.toDtoList(auditorias);
        return ResponseEntity.ok(auditoriaDtos);
    }

    //Obtener una auditoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDto> getAuditoriaById(@PathVariable Long id) {
        return auditoriaService.findById(id)
                .map(auditoriaMapper::toAuditoriaDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "auditoria no encontrada"));
    }

    //  No hay POST, PUT o DELETE ya que la auditoría se genera automáticamente
    //  al realizar otras operaciones en el sistema.
}
