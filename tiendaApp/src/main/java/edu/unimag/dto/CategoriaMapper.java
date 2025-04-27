package edu.unimag.dto;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Categoria;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriaMapper {

    public CategoriaDto toCategoriaDto(Categoria categoria) { 
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setNombre(categoria.getNombre());
        categoriaDto.setDescripcion(categoria.getDescripcion());
        return categoriaDto;
    }

    public Categoria toCategoria(CategoriaCreateDto categoriaCreateDto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaCreateDto.getNombre());
        categoria.setDescripcion(categoriaCreateDto.getDescripcion());
        return categoria;
    }

    public List<CategoriaDto> toDtoList(List<Categoria> categorias) {
        List<CategoriaDto> categoriaDtos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriaDtos.add(toCategoriaDto(categoria));
        }
        return categoriaDtos;
    }
}
