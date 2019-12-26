package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * @author Macario
 *
 */
public class ClienteNoAutorizadoException extends Exception {
    /**
     * @param nif identifica el dni.
     * @param identificador identifica la cuenta.
     */
    public ClienteNoAutorizadoException(final String nif,
            final Long identificador) {
        super("El cliente con NIF " + nif
                + " no está autorizado para operar en la cuenta "
                + identificador);
    }
}
