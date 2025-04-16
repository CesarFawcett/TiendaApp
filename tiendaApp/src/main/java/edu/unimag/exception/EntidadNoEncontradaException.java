package edu.unimag.exception;

/**
 * Excepción lanzada cuando una entidad solicitada no existe en el sistema.
 * <p>
 * Comúnmente ocurre en operaciones de búsqueda, actualización o eliminación
 * cuando el identificador proporcionado no corresponde a ninguna entidad.
 * </p>
 */
public class EntidadNoEncontradaException extends EntidadException {
    /**
     * Construye una nueva excepción indicando la entidad y ID no encontrado.
     *
     * @param nombreEntidad el nombre de la entidad que no fue encontrada
     * @param id el identificador utilizado en la búsqueda
     */
    public EntidadNoEncontradaException(String nombreEntidad, Long id) {
        super(String.format("%s con ID %d no encontrada", nombreEntidad, id));
    }
}
