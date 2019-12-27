package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**.
 *
 * Esta clase
 *
 * @author: julio
 *
 * @version: 22/09/2019/A
 */
public class CuentaInvalidaException extends Exception {
    /**
     *
     *@param numero is
     */
    public CuentaInvalidaException(final Long numero) {
        super("La cuenta " + numero + " no existe");
    }

}