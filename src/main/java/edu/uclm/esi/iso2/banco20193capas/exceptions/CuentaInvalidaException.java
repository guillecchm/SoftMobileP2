package edu.uclm.esi.iso2.banco20193capas.exceptions;
/** 
 * @author Macario
 */
public class CuentaInvalidaException extends Exception {
    /**
     * @param numero identifica el numero de la cuenta.
     */
    public CuentaInvalidaException(final Long numero) {
        super("La cuenta " + numero + " no existe");
    }

}
