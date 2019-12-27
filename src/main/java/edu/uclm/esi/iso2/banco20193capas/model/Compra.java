package edu.uclm.esi.iso2.banco20193capas.model;

/**
 *
 * @author aspi4
 *
 */
public class Compra {
    /**
     *
     */
    private double importe;
    /**
     *
     */
    private int token;

    /**
     *
     *@param importe is
     *@param token is
     */
    public Compra(final double importe, final int token) {
        this.importe = importe;
        this.token = token;
    }

    /**
     *@return is
     *
     */
    public double getImporte() {
        return importe;
    }

    /**
     *@param importe is
     *
     */
    public void setImporte(final double importe) {
        this.importe = importe;
    }

    /**
     *@return is
     *
     */
    public int getToken() {
        return token;
    }

    /**
     *@param token is
     *
     */
    public void setToken(final int token) {
        this.token = token;
    }

}