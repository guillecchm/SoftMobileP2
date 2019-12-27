package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 *
 * @author Macario
 *
 */
public class TarjetaBloqueadaException extends Exception {
    /**
     * Excepción que se produce cuando la tarjeta se bloquea debido
     * a introducir 3 veces mal el pin.
     */
	public TarjetaBloqueadaException() {
		super("La tarjeta está bloqueada");
	}
}
