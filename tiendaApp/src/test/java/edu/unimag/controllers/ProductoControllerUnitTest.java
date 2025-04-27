package edu.unimag.controllers;

import edu.unimag.dto.ProductoCreateDto;
import edu.unimag.dto.ProductoDto;
import edu.unimag.dto.ProductoMapper;
import edu.unimag.entities.Producto;
import edu.unimag.entities.Categoria;
import edu.unimag.exception.EntidadNoEncontradaException;
import edu.unimag.services.ProductoService;
import edu.unimag.services.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoControllerUnitTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;

    @Mock
    private ProductoMapper productoMapper;

    @Mock
    private CategoriaService categoriaService;

    private Producto producto;
    private ProductoDto productoDto;
    private ProductoCreateDto productoCreateDto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria(1L, "Test Category", "Test Description");
        producto = new Producto(1L, "Test Product", "Test Description", 10.0, 5, LocalDate.now(), categoria);
        productoDto = new ProductoDto(1L, "Test Product", "Test Description", 10.0, 5, LocalDate.now());
        productoCreateDto = new ProductoCreateDto("Test Product", "Test Description", 10.0, 5, LocalDate.now(), 1L);

        when(categoriaService.findById(1L)).thenReturn(Optional.of(categoria));
    }

    @Test
    void createProducto_Returns201Created_WhenProductoIsCreated() {
        when(productoMapper.toProducto(productoCreateDto)).thenReturn(producto);
        when(productoService.create(producto)).thenReturn(producto);
        when(productoMapper.toProductoDto(producto)).thenReturn(productoDto);

        ResponseEntity<ProductoDto> response = productoController.createProducto(productoCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoDto, response.getBody());
        verify(productoService, times(1)).create(producto);
    }

    @Test
    void getAllProductos_Returns200OKWithListOfProductoDtos() {
        List<Producto> productos = List.of(producto);
        List<ProductoDto> productoDtos = List.of(productoDto);

        when(productoService.findAll()).thenReturn(productos);
        when(productoMapper.toDtoList(productos)).thenReturn(productoDtos);

        ResponseEntity<List<ProductoDto>> response = productoController.getAllProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoDtos, response.getBody());
        verify(productoService, times(1)).findAll();
    }

    @Test
    void getProductoById_Returns200OKWithProductoDto_WhenProductoExists() {
        when(productoService.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toProductoDto(producto)).thenReturn(productoDto);

        ResponseEntity<ProductoDto> response = productoController.getProductoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoDto, response.getBody());
        verify(productoService, times(1)).findById(1L);
    }

    @Test
    void getProductoById_Throws404NotFound_WhenProductoDoesNotExist() {
        when(productoService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productoController.getProductoById(1L));

        verify(productoService, times(1)).findById(1L);
    }

     @Test
    void updateProducto_Returns200OK_WhenProductoIsUpdated() throws EntidadNoEncontradaException {
        when(productoMapper.toProducto(any(ProductoCreateDto.class))).thenReturn(producto);
        when(productoService.update(eq(1L), any(Producto.class))).thenReturn(producto);
        when(productoMapper.toProductoDto(producto)).thenReturn(productoDto);

        ResponseEntity<ProductoDto> response = productoController.updateProducto(1L, productoCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoDto, response.getBody());
        verify(productoService, times(1)).update(eq(1L), any(Producto.class));
    }

    @Test
    void updateProducto_Returns404NotFound_WhenProductoDoesNotExist() throws EntidadNoEncontradaException {
         when(productoMapper.toProducto(any(ProductoCreateDto.class))).thenReturn(producto);
        when(productoService.update(eq(1L), any(Producto.class))).thenThrow(new EntidadNoEncontradaException("Producto", 1L));

        ResponseEntity<ProductoDto> response = productoController.updateProducto(1L, productoCreateDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productoService, times(1)).update(eq(1L), any(Producto.class));
    }

    @Test
    void deleteProducto_Returns204NoContent_WhenProductoIsDeleted() {
        ResponseEntity<Void> response = productoController.deleteProducto(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productoService, times(1)).delete(1L);
    }
}