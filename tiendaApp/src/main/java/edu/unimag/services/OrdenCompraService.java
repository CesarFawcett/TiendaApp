package edu.unimag.services;

import java.util.List;
import java.util.Optional;

import edu.unimag.dto.DetallesOrdenCompraCreateDto;
import edu.unimag.entities.DetallesOrdenCompra;
import edu.unimag.entities.EstadoOrden;
import edu.unimag.entities.OrdenCompra;
import edu.unimag.entities.Usuario;

public interface OrdenCompraService {
    
    List<OrdenCompra> findAll();
    OrdenCompra create (OrdenCompra newOrdenCompra);
    Optional<OrdenCompra> findById(Long id);
    OrdenCompra update(long id, OrdenCompra newOrdenCompra);
    void delete(Long id);
    OrdenCompra addDetalle(Long ordenCompraId, DetallesOrdenCompraCreateDto dto);
    List<DetallesOrdenCompra> getDetalles(Long ordenCompraId);
    OrdenCompra cambiarEstado(Long ordenCompraId, EstadoOrden nuevoEstado, Usuario usuario, String observacion);

}
