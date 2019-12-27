package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 *
 * @author Macario
 *
 */
public class CuentaSinTitularesException extends Exception {
    /**
     * Excepción que se produce cuando una cuenta no tiene titular.
     */
    public CuentaSinTitularesException() {
        super("Falta indicar el titular o titulares");
    }
}