package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * @author Macario.
 */
public class SaldoInsuficienteException extends Exception {
    /**
     * @author Macario.
     */
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para el importe solicitado");
    }
}
