package edu.uclm.esi.iso2.banco20193capas.model;

import java.security.SecureRandom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TokenInvalidoException;

/**
 * Representa una tarjeta bancaria, bien de débito o bien de crédito. Una
 * {@code Tarjeta} está asociada a un {@code Cliente} y a una {@code Cuenta}.
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractTarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected Integer pin;
    protected Boolean activa;
    public Integer intentos;

    @Transient
    protected Compra compra;

    @ManyToOne
    protected Cliente titular;

    @ManyToOne
    public Cuenta cuenta;

    /**
     * Inicializa la tarjeta con un pin aleatorio.
     */
    public AbstractTarjeta() {
        activa = true;
        this.intentos = 0;
        final SecureRandom dado = new SecureRandom();
        pin = 0;

        for (int i = 0; i <= 3; i++) {
            pin = (int) (pin + dado.nextInt(10) * Math.pow(10, i));
        }

    }

    /**
     * Permite comprobar si el pin de la tarjeta es válido o no.
     * En caso de no ser válida por 3 ocasiones seguidas, se bloquea.
     * @param pin El pin proporcionado por el usuario.
     * @throws TarjetaBloqueadaException Si se introduce el pin 3 veces mal.
     * @throws PinInvalidoException Si el {@code pin} es incorrecto
     *
     */
    public void comprobar(final int pin)
            throws TarjetaBloqueadaException, PinInvalidoException {
        if (!this.isActiva()) {
            throw new TarjetaBloqueadaException();
        }

        if (this.pin != pin) {
            this.intentos = this.intentos + 1;

            if (intentos == 3) {
                bloquear();
            }

            throw new PinInvalidoException();
        }
    }

    /**
     * Permite confirmar una compra que se ha iniciado por Internet. El método
     * {@link #comprarPorInternet(int, double)} devuelve un token que debe ser
     * introducido en este método.
     *
     * @param token El token que introduce el usuario. Para que la compra se
     *        confirme, ha de coincidir con el token devuelto por
     *        {@link #comprarPorInternet(int, double)}
     * @throws TokenInvalidoException Si el {@code token} introducido es
     *         distinto del recibido desde
     *         {@link #comprarPorInternet(int, double)}
     * @throws ImporteInvalidoException Si el importe es menor o igual que 0
     * @throws SaldoInsuficienteException Si el saldo de la cuenta asociada a la
     *         tarjeta (en el caso de {@link TarjetaDebito}) es menor que el
     *         importe, o si el crédito disponible en la tarjeta de crédito es
     *         menor que el importe
     * @throws TarjetaBloqueadaException Si la tarjeta está bloqueada
     * @throws PinInvalidoException Si el pin que se introdujo es inválido
     */
    public void confirmarCompraPorInternet(final int token)
            throws TokenInvalidoException, ImporteInvalidoException,
            SaldoInsuficienteException, TarjetaBloqueadaException,
            PinInvalidoException {
        if (token != this.compra.getToken()) {
            this.compra = null;
            throw new TokenInvalidoException();
        }
        this.comprar(this.pin, this.compra.getImporte());
    }

    protected abstract void bloquear();

    /**
     * Return id de la tarjeta.
     * @return id
     */
    public final Long getId() {
        return id;
    }

    public final void setId(final Long identification) {
        this.id = identification;
    }

    /**
     * Obtener pin de la tarjeta.
     * @return pin
     */
    public final Integer getPin() {
        return pin;
    }

    public final void setPin(final Integer pin) {
        this.pin = pin;
    }

    /**
     * Obtener titular de la tarjeta.
     * @return titular
     */
    public final Cliente getTitular() {
        return titular;
    }

    public final void setTitular(final Cliente titular) {
        this.titular = titular;
    }

    /**
     * Obtener cuenta.
     * @return cuenta
     */
    public final Cuenta getCuenta() {
        return cuenta;
    }


    public final void setCuenta(final Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     *
     * @return true si la tarjeta está activa; false si está bloqueada
     */
    public Boolean isActiva() {
        return activa;
    }

    /**
     *
     * @param activa Establece si la tarjeta está activa.
     *
     */
    public final void setActiva(final Boolean activa) {
        this.activa = activa;
    }

    public abstract void sacarDinero(int pin, double importe)
            throws ImporteInvalidoException, SaldoInsuficienteException,
            TarjetaBloqueadaException, PinInvalidoException;

    public abstract Integer comprarPorInternet(int pin, double importe)
            throws TarjetaBloqueadaException, PinInvalidoException,
            SaldoInsuficienteException, ImporteInvalidoException;

    public abstract void comprar(int pin, double importe)
            throws ImporteInvalidoException, SaldoInsuficienteException,
            TarjetaBloqueadaException, PinInvalidoException;

    /**
     * Permite cambiar el pin de la tarjeta.
     *
     * @param pinViejo El pin actual
     * @param pinNuevo El pin nuevo (el que desea establecer el usuario)
     * @throws PinInvalidoException Si el {@code pinViejo} es incorrecto
     *
     */
    public abstract void cambiarPin(int pinViejo, int pinNuevo)
            throws PinInvalidoException;
}

