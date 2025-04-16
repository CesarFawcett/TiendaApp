package edu.unimag.exception;

/**
 * Excepción lanzada cuando se intenta crear o actualizar una entidad que viola
 * restricciones de unicidad en el sistema.
 * <p>
 * Indica que ya existe una entidad con los mismos valores en campos que deben ser únicos.
 * </p>
 */
public class EntidadDuplicadaException extends EntidadException {
    /**
     * Construye una nueva excepción indicando la entidad y campo duplicado.
     *
     * @param nombreEntidad el nombre de la entidad que causó el conflicto
     * @param campo el campo o combinación de campos que violan la unicidad
     */
    public EntidadDuplicadaException(String nombreEntidad, String campo) {
        super(String.format("%s con %s ya existe", nombreEntidad, campo));
    }
}
