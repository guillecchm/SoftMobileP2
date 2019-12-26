package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * clase ImporteInvalidoException.
 * @author Macario
 */
public class ImporteInvalidoException extends Exception {
    /**
     * exception.
     * @param importe
     */
    public ImporteInvalidoException(final double importe) {
        super("El importe " + importe + " no es válido para esta operación");
    }
}
