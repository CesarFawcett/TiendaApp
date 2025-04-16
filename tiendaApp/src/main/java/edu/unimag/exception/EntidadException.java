package edu.unimag.exception;

/**
 * Clase base para todas las excepciones relacionadas con entidades en el sistema.
 * <p>
 * Proporciona una estructura común para excepciones que ocurren durante operaciones
 * relacionadas con entidades del dominio (como creación, actualización, búsqueda, etc.).
 * </p>
 */
public class EntidadException extends RuntimeException {
    /**
     * Construye una nueva excepción con el mensaje de detalle especificado.
     *
     * @param message el mensaje de detalle (que se guarda para luego recuperarlo
     *                mediante el método {@link #getMessage()}).
     */
    public EntidadException(String message) {
        super(message);
    }
    
    /**
     * Construye una nueva excepción con el mensaje de detalle especificado y la causa.
     * <p>
     * Es útil para propagar excepciones de bajo nivel preservando el stack trace original.
     * </p>
     * @param message el mensaje de detalle (que se guarda para luego recuperarlo
     *                mediante el método {@link #getMessage()}).
     * @param cause la causa (que se guarda para luego recuperarla mediante el método
     *              {@link #getCause()}). Un valor {@code null} indica que la causa
     *              es inexistente o desconocida.
     */
    public EntidadException(String message, Throwable cause) {
        super(message, cause);
    }
}