package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * . Representa un movimiento asociado a una tarjeta de crédito
 *
 */
@Entity
public class MovimientoTarjetaCredito {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     *
     */
    @ManyToOne
    private TarjetaCredito tarjeta;
    /**
     *
     */
    private double importe;

    /**
     *
     */
    private String concepto;
    /**
     *
     */
    private boolean liquidado;

    /**
     *
     */
    public MovimientoTarjetaCredito() {
    }

    /**
     *
     *@param concepto is
     *@param tarjeta is
     *@param importe is
     */
    public MovimientoTarjetaCredito(final TarjetaCredito tarjeta,
            final double importe, final String concepto) {
        this.importe = importe;
        this.concepto = concepto;
        this.tarjeta = tarjeta;
    }

    /**
     * @return is
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id is
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     *
     * @return is
     */
    public TarjetaCredito getTarjeta() {
        return tarjeta;
    }

    /**
     * @param tarjeta is
     *
     */
    public void setTarjeta(final TarjetaCredito tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * @return is
     *
     */
    public double getImporte() {
        return importe;
    }

    /**
     * @param importe is
     *
     */
    public void setImporte(final double importe) {
        this.importe = importe;
    }

    /**
     * @return is
     *
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto is
     *
     */
    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }

    /**
     *@return is
     *
     */
    public boolean isLiquidado() {
        return liquidado;
    }

    /**
     *
     *@param liquidado is
     */
    public void setLiquidado(final boolean liquidado) {
        /**
         *
         */
        this.liquidado = liquidado;
    }

}