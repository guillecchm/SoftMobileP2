package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 *
 * @author Macario
 *
 */
public class PinInvalidoException extends Exception {
    /**
     * Excepción que se produce cuando el cliente introduce mal el pin.
     */
	public PinInvalidoException() {
		super("PIN inválido");
	}
}
