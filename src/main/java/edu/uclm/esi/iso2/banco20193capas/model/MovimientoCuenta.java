package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Representa un movimiento en una cuenta bancaria.
 * @author Macario
 */
@Entity
public class MovimientoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Cuenta cuenta;
    private double importe;
    private String concepto;

    /**
     * empty constructor.
     */
    public MovimientoCuenta() {
    }

    /**
     * Constructor.
     * 
     * @param cuenta
     * @param importe
     * @param concepto
     */
    public MovimientoCuenta(final Cuenta cuenta, final double importe,
            final String concepto) {
        this.importe = importe;
        this.concepto = concepto;
        this.cuenta = cuenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(final Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(final double importe) {
        this.importe = importe;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }
}
