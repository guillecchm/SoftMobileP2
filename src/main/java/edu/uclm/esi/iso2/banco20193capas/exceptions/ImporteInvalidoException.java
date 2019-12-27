package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * Clase ImporteInvalidoException.
 * @author Macario
 *
 */
public class ImporteInvalidoException extends Exception {
    /**
     * Imprime mensaje importe no válido.
     * @param importe Importe de la operación.
     */
    public ImporteInvalidoException(final double importe) {
        super("El importe " + importe + " no es válido para esta operación");
    }
}
