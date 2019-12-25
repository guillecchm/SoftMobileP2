package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoTarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLucía extends TestCase{
	private TarjetaCredito tarjetaCVarian;
	private TarjetaDebito tarjetaDVarian;
	private Cliente varian;
	private Cuenta cuentaVarian;
	
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		this.varian = new Cliente("01010A", "Varian", "Wrynn");
		this.varian.insert();
		this.tarjetaCVarian = new TarjetaCredito();
		this.tarjetaDVarian = new TarjetaDebito();
		this.cuentaVarian = new Cuenta(1);

		try {
			this.cuentaVarian.addTitular(varian);
			this.cuentaVarian.insert();
			this.cuentaVarian.ingresar(20000);
			this.tarjetaCVarian = this.cuentaVarian.emitirTarjetaCredito(varian.getNif(), 2000);
			this.tarjetaCVarian.cambiarPin(this.tarjetaCVarian.getPin(), 1234);
			this.tarjetaDVarian = this.cuentaVarian.emitirTarjetaDebito(varian.getNif());
			this.tarjetaDVarian.cambiarPin(this.tarjetaDVarian.getPin(), 1234);
		} catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}
	}
	
	@Test
	public void testSacarDineroC() {
		
	}
	
	@Test
	public void testSacarDineroD() {
		int pin = 1234;
		int importe = 20;
		try {
			this.tarjetaDVarian.comprobar(pin);
			assertTrue(pin == tarjetaDVarian.getPin());
			this.tarjetaDVarian.cuenta.retirar(importe);
			assertTrue(tarjetaDVarian.getCuenta().getSaldo() == 19980);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}	
	
	
	@Test
	public void testAddTitular() {
		
	}

}
