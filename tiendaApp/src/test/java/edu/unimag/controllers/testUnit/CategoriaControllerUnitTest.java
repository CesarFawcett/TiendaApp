package edu.unimag.controllers.testUnit;

import edu.unimag.dto.CategoriaDto;
import edu.unimag.controllers.CategoriaController;
import edu.unimag.dto.CategoriaCreateDto;
import edu.unimag.dto.CategoriaMapper;
import edu.unimag.entities.Categoria;
import edu.unimag.services.CategoriaService;
import edu.unimag.exception.EntidadNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoriaControllerUnitTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private CategoriaMapper categoriaMapper;

    private Categoria categoria;
    private CategoriaDto categoriaDto;
    private CategoriaCreateDto categoriaCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        categoria = new Categoria(1L, "Test", "Test Description");
        categoriaDto = new CategoriaDto(1L, "Test", "Test Description");
        categoriaCreateDto = new CategoriaCreateDto("Test", "Test Description");
    }

    @Test
    void createCategoria_Returns201Created_WhenCategoriaIsCreated() {
        when(categoriaMapper.toCategoria(categoriaCreateDto)).thenReturn(categoria);
        when(categoriaService.create(categoria)).thenReturn(categoria);
        when(categoriaMapper.toCategoriaDto(categoria)).thenReturn(categoriaDto);
        ResponseEntity<CategoriaDto> response = categoriaController.createCategoria(categoriaCreateDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoriaDto, response.getBody());
        verify(categoriaService, times(1)).create(categoria);
    }

    @Test
    void getAllCategorias_Returns200OKWithListOfCategoriaDtos() {
        List<Categoria> categorias = List.of(categoria);
        List<CategoriaDto> categoriaDtos = List.of(categoriaDto);

        when(categoriaService.findAll()).thenReturn(categorias);
        when(categoriaMapper.toDtoList(categorias)).thenReturn(categoriaDtos);
        ResponseEntity<List<CategoriaDto>> response = categoriaController.getAllCategorias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriaDtos, response.getBody());
        verify(categoriaService, times(1)).findAll();
    }

    @Test
    void getCategoriaById_Returns200OKWithCategoriaDto_WhenCategoriaExists() {
        when(categoriaService.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toCategoriaDto(categoria)).thenReturn(categoriaDto);
        ResponseEntity<CategoriaDto> response = categoriaController.getCategoriaById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriaDto, response.getBody());
        verify(categoriaService, times(1)).findById(1L);
    }

    @Test
    void getCategoriaById_Throws404NotFound_WhenCategoriaDoesNotExist() {
        when(categoriaService.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> categoriaController.getCategoriaById(1L));
        verify(categoriaService, times(1)).findById(1L);
    }

    @Test
    void updateCategoria_Returns200OKWithUpdatedCategoriaDto_WhenCategoriaIsUpdated() throws EntidadNoEncontradaException {
        when(categoriaMapper.toCategoria(categoriaCreateDto)).thenReturn(categoria);
        when(categoriaService.update(eq(1L), any(Categoria.class))).thenReturn(categoria);
        when(categoriaMapper.toCategoriaDto(categoria)).thenReturn(categoriaDto);
        ResponseEntity<CategoriaDto> response = categoriaController.updateCategoria(1L, categoriaCreateDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriaDto, response.getBody());
        verify(categoriaService, times(1)).update(eq(1L), any(Categoria.class));
    }

    @Test
    void updateCategoria_Returns404NotFound_WhenCategoriaDoesNotExist() throws EntidadNoEncontradaException {
        when(categoriaMapper.toCategoria(categoriaCreateDto)).thenReturn(categoria);
        when(categoriaService.update(eq(1L), any(Categoria.class))).thenThrow(new EntidadNoEncontradaException("Categoria", 1L));
        ResponseEntity<CategoriaDto> response = categoriaController.updateCategoria(1L, categoriaCreateDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoriaService, times(1)).update(eq(1L), any(Categoria.class));
    }

    @Test
    void updateCategoria_Returns400BadRequest_WhenIllegalArgumentExceptionIsThrown() throws EntidadNoEncontradaException {
        when(categoriaMapper.toCategoria(categoriaCreateDto)).thenReturn(categoria);
        when(categoriaService.update(eq(1L), any(Categoria.class))).thenThrow(new IllegalArgumentException("Invalid data"));
        ResponseEntity<CategoriaDto> response = categoriaController.updateCategoria(1L, categoriaCreateDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoriaService, times(1)).update(eq(1L), any(Categoria.class));
    }

    @Test
    void deleteCategoria_Returns204NoContent_WhenCategoriaIsDeleted() {
        ResponseEntity<Void> response = categoriaController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoriaService, times(1)).delete(1L);
    }
}
