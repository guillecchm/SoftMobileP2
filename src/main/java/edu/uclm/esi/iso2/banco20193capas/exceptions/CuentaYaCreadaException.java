package edu.uclm.esi.iso2.banco20193capas.exceptions;
/**
 *
 * @author aspi4
 *
 */
public class CuentaYaCreadaException extends Exception {
    /**.
     *
     * Esta clase
     *
     * @author: julio
     *
     * @version: 22/09/2019/A
     */
    public CuentaYaCreadaException() {
        super("La cuenta está creada y no admite añadir titulares");
    }
}