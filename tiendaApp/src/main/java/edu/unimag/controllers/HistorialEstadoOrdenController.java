package edu.unimag.controllers;

import edu.unimag.entities.HistorialEstadoOrden;
import edu.unimag.dto.HistorialEstadoOrdenDto;
import edu.unimag.dto.HistorialEstadoOrdenMapper;
import edu.unimag.services.HistorialEstadoOrdenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/historial-estados-orden")
public class HistorialEstadoOrdenController {

    private final HistorialEstadoOrdenService historialEstadoOrdenService;
    private final HistorialEstadoOrdenMapper historialEstadoOrdenMapper;

    public HistorialEstadoOrdenController(HistorialEstadoOrdenService historialEstadoOrdenService,
                                          HistorialEstadoOrdenMapper historialEstadoOrdenMapper) {
        this.historialEstadoOrdenService = historialEstadoOrdenService;
        this.historialEstadoOrdenMapper = historialEstadoOrdenMapper;
    }

    //Obtener todos los historiales
    @GetMapping
    public ResponseEntity<List<HistorialEstadoOrdenDto>> getAllHistorialesEstadoOrden() {
        List<HistorialEstadoOrden> historiales = historialEstadoOrdenService.findAll();
        List<HistorialEstadoOrdenDto> historialesDtos = historialEstadoOrdenMapper.toDtoList(historiales);
        return ResponseEntity.ok(historialesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoOrdenDto> getHistorialEstadoOrdenById(@PathVariable Long id) {
        return historialEstadoOrdenService.findById(id)
                .map(historialEstadoOrdenMapper::toHistorialEstadoOrdenDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historiales no encontrada"));
    
    }
   
    //@PostMapping
    //public ResponseEntity<HistorialEstadoOrdenDto> createHistorialEstadoOrden(@Valid @RequestBody HistorialEstadoOrdenCreateDto historialEstadoOrdenCreateDto) {
    //    }

    //  No hay PUT ya que el historial se crea al cambiar el estado de la orden

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialEstadoOrden(@PathVariable("id") Long id) {
        historialEstadoOrdenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
