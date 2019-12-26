package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**.
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

    /**.
     * empty constructor.
     * this constructor is empty.
     */
    public MovimientoCuenta() {
    }

    /**.
     * Constructor.
     * @param cuentA
     * @param importE
     * @param conceptO
     */
    public MovimientoCuenta(final Cuenta cuentA, final double importE,
            final String conceptO) {
        this.importe = importE;
        this.concepto = conceptO;
        this.cuenta = cuentA;
    }

    /**.
     * getID().
     * @return id
     */
    public Long getId() {
        return id;
    }

    public void setId(final Long identification) {
        this.id = identification;
    }

    /**.
     * getCuenta().
     * @return cuenta
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(final Cuenta cuentA) {
        this.cuenta = cuentA;
    }

    /**.
     * getImporte().
     * @return importe
     */
    public double getImporte() {
        return importe;
    }

    public void setImporte(final double importE) {
        this.importe = importE;
    }

    /**.
     * getConcepto().
     * @return concepto
     */
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(final String conceptO) {
        this.concepto = conceptO;
    }
}
