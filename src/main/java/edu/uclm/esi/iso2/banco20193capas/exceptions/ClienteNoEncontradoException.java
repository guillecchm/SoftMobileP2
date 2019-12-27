package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 *
 * @author Macario
 *
 */
public class ClienteNoEncontradoException extends Exception {
    /**
     * Excepción que se produce cuando no se encuentra un cliente para
     * asignar a una tarjeta.
     * @param nif Identificacion del cliente.
     */
    public ClienteNoEncontradoException(final String nif) {
        super("No se encuentra el cliente con NIF " + nif);
    }
}