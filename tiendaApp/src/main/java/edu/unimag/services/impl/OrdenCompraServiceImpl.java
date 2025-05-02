package edu.unimag.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unimag.dto.DetallesOrdenCompraCreateDto;
import edu.unimag.entities.DetallesOrdenCompra;
import edu.unimag.entities.EstadoOrden;
import edu.unimag.entities.OrdenCompra;
import edu.unimag.entities.Producto;
import edu.unimag.entities.Usuario;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.repositories.DetallesOrdenCompraRepository;
import edu.unimag.repositories.OrdenCompraRepository;
import edu.unimag.repositories.ProductoRepository;
import edu.unimag.services.OrdenCompraService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    private final ProductoRepository productoRepository;
    private final DetallesOrdenCompraRepository detallesOrdenCompraRepository;

    public OrdenCompraServiceImpl(OrdenCompraRepository ordenCompraRepository,
                                  ProductoRepository productoRepository,
                                  DetallesOrdenCompraRepository detallesOrdenCompraRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.productoRepository = productoRepository;
        this.detallesOrdenCompraRepository = detallesOrdenCompraRepository;
    }

    @Override
    public List<OrdenCompra> findAll() {
        return ordenCompraRepository.findAll();
        }

    @Override
    public OrdenCompra create(OrdenCompra newOrdenCompra) {
        return ordenCompraRepository.save(newOrdenCompra);
        }

    @Override
    public Optional<OrdenCompra> findById(Long id) {
        return ordenCompraRepository.findById(id);
       }

    @Override
    public OrdenCompra update(long id, OrdenCompra newOrdenCompra) {
    Optional<OrdenCompra> existingOrdenCompra = ordenCompraRepository.findById(id);
    if (existingOrdenCompra.isPresent()){
        OrdenCompra ordenCompraToUpdate = existingOrdenCompra.get();
        ordenCompraToUpdate.setFecha(newOrdenCompra.getFecha());
        ordenCompraToUpdate.setEstado(newOrdenCompra.getEstado());
        ordenCompraToUpdate.setProveedor(newOrdenCompra.getProveedor());
        return ordenCompraRepository.save(ordenCompraToUpdate);
    }else {
        return null;
    }
    }

    @Override
    public void delete(Long id) {
        ordenCompraRepository.deleteById(id);
       }

    @Override
    public OrdenCompra addDetalle(Long ordenCompraId, DetallesOrdenCompraCreateDto dto) {
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
            .orElseThrow(() -> new EntidadNoEncontradaException("OrdenCompra", ordenCompraId));
        
        Producto producto = productoRepository.findById(dto.getProductoId())
            .orElseThrow(() -> new EntidadNoEncontradaException("Producto", dto.getProductoId()));

        DetallesOrdenCompra detalle = new DetallesOrdenCompra();
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setProducto(producto);
        detalle.setOrdenCompra(ordenCompra);

        detallesOrdenCompraRepository.save(detalle);
        ordenCompra.addDetalle(detalle);
        
        return ordenCompraRepository.save(ordenCompra);
    }

    @Override
    public List<DetallesOrdenCompra> getDetalles(Long ordenCompraId) {
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
            .orElseThrow(() -> new EntidadNoEncontradaException("OrdenCompra", ordenCompraId));
        return ordenCompra.getDetalles();
    }

    @Override
    public OrdenCompra cambiarEstado(Long ordenCompraId, EstadoOrden nuevoEstado, Usuario usuario, String observacion) {
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
            .orElseThrow(() -> new EntidadNoEncontradaException("OrdenCompra", ordenCompraId));

        ordenCompra.cambiarEstado(nuevoEstado, usuario, observacion);
        return ordenCompraRepository.save(ordenCompra);
    }

}
