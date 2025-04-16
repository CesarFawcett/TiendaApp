package edu.unimag.exception;


/**
 * Excepción lanzada cuando una entidad no cumple con las validaciones de negocio
 * o tiene un estado inválido para la operación solicitada.
 * <p>
 * Puede ocurrir durante operaciones de creación, actualización o procesamiento
 * de entidades.
 * </p>
 */
public class EntidadInvalidaException extends EntidadException {
    /**
     * Construye una nueva excepción indicando la entidad y razón de invalidación.
     *
     * @param nombreEntidad el nombre de la entidad que no pasó las validaciones
     * @param razon descripción detallada de por qué la entidad es inválida
     */
    public EntidadInvalidaException(String nombreEntidad, String razon) {
        super(String.format("%s inválida: %s", nombreEntidad, razon));
    }
}
