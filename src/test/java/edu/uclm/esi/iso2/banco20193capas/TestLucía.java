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
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
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
			this.tarjetaCVarian = this.cuentaVarian.emitirTarjetaCredito(varian.getNif(), 0);
			this.tarjetaCVarian.cambiarPin(this.tarjetaCVarian.getPin(), 1234);
			this.tarjetaDVarian = this.cuentaVarian.emitirTarjetaDebito(varian.getNif());
			this.tarjetaDVarian.cambiarPin(this.tarjetaDVarian.getPin(), 1234);
		} catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}
	}
	
	@Test
	public void testSacarDineroCImporteNulo() {
		try {
			this.tarjetaCVarian.sacarDinero(1234, 0.0);
		} catch (ImporteInvalidoException e) {
			fail("Importe inválido: " + e.getMessage());
		} catch (SaldoInsuficienteException | TarjetaBloqueadaException | PinInvalidoException e) {
			fail("Una excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testSacarDineroCImporteExacto() {
		try {
			this.tarjetaCVarian.sacarDinero(1234, 20000);
			assertTrue(this.tarjetaCVarian.getCuenta().getSaldo() == 0.0);
		} catch (ImporteInvalidoException e) {
			fail("Importe inválido: " + e.getMessage());
		} catch (SaldoInsuficienteException | TarjetaBloqueadaException | PinInvalidoException e) {
			fail("Una excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testSacarDineroDImporteNulo() {
		try {
			this.tarjetaDVarian.sacarDinero(1234, 0.0);
		} catch (TarjetaBloqueadaException | SaldoInsuficienteException | PinInvalidoException e) {
			fail("Una excepción: " + e.getMessage());
		} catch (ImporteInvalidoException e) {
			fail("Importe inválido: " + e.getMessage());
		}
	}
	
	@Test
	public void testSacarDineroDImporteExacto() {
		try {
			this.tarjetaDVarian.sacarDinero(1234, 20000);
			assertTrue(this.tarjetaDVarian.getCuenta().getSaldo() == 0.0);
		} catch (TarjetaBloqueadaException | SaldoInsuficienteException | PinInvalidoException | ImporteInvalidoException e) {
			fail("Una excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testSacarDineroDImporteDemasiadoGrande() {
		try {
			this.tarjetaDVarian.sacarDinero(1234, 99999);
		} catch (TarjetaBloqueadaException | PinInvalidoException | ImporteInvalidoException e) {
			fail("Una excepción: " + e.getMessage());
		} catch ( SaldoInsuficienteException e) {
			fail("Saldo insuficiente: " + e.getMessage());
		}
	}
	
	
	@Test
	public void testAddTitular() {
		
	}

}
