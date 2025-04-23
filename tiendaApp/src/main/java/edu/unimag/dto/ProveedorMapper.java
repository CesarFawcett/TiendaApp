package edu.unimag.dto;

import org.springframework.stereotype.Component;
import edu.unimag.entities.Proveedor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProveedorMapper {
    
    public ProveedorDto toProveedorDto(Proveedor proveedor) {
        ProveedorDto proveedorDto = new ProveedorDto();
        proveedorDto.setId(proveedor.getId());
        proveedorDto.setNombre(proveedor.getNombre());
        proveedorDto.setContacto(proveedor.getContacto());
        proveedorDto.setTelefono(proveedor.getTelefono());
        proveedorDto.setEmail(proveedor.getEmail());
        proveedorDto.setDireccion(proveedor.getDireccion());
        return proveedorDto;
    }
    
    public Proveedor toProveedor(ProveedorCreateDto proveedorCreateDto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorCreateDto.getNombre());
        proveedor.setContacto(proveedorCreateDto.getContacto());
        proveedor.setTelefono(proveedorCreateDto.getTelefono());
        proveedor.setEmail(proveedorCreateDto.getEmail());
        proveedor.setDireccion(proveedorCreateDto.getDireccion());
        return proveedor;
    }
    
    public List<ProveedorDto> toDtoList(List<Proveedor> proveedores) {
        List<ProveedorDto> proveedorDtos = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            proveedorDtos.add(toProveedorDto(proveedor));
        }
        return proveedorDtos;
    }
}