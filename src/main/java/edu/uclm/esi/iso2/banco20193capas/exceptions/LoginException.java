package edu.uclm.esi.iso2.banco20193capas.exceptions;

/**
 * clase LoginException.
 * @author Macario
 */
public class LoginException extends Exception {
    /**
     * constructor.
     */
    public LoginException() {
        super("Credenciales inválidas");
    }
}
