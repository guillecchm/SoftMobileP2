package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * 
 * @author Macario
 *
 */
public class SaldoInsuficienteException extends Exception {
    /**
     * Excepción que se produce cuando se intenta sacar más dinero que
     * saldo se tiene en una cuenta.
     */
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para el importe solicitado");
    }
}