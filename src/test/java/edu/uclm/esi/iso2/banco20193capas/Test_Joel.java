package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_Joel extends TestCase {
	private Cuenta cuentaPepe, cuentaAna;
	private Cliente pepe, ana;
	private TarjetaDebito tdPepe, tdAna;
	private TarjetaCredito tcPepe, tcAna;
	
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		this.pepe = new Cliente("12345X", "Pepe", "Pérez"); this.pepe.insert();
		this.ana = new Cliente("98765F", "Ana", "López"); this.ana.insert();
		this.cuentaPepe = new Cuenta(1); this.cuentaAna = new Cuenta(2);
		try {
			this.cuentaPepe.addTitular(pepe); this.cuentaPepe.insert(); this.cuentaPepe.ingresar(1000);
			this.cuentaAna.addTitular(ana); this.cuentaAna.insert(); this.cuentaAna.ingresar(5000);
			this.tcPepe = this.cuentaPepe.emitirTarjetaCredito(pepe.getNif(), 2000);
			this.tcAna = this.cuentaAna.emitirTarjetaCredito(ana.getNif(), 10000);
			this.tdPepe = this.cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			this.tdAna = this.cuentaAna.emitirTarjetaDebito(ana.getNif());
			
			this.tcPepe.cambiarPin(tcPepe.getPin(), 1234);
			this.tcAna.cambiarPin(tcAna.getPin(), 1234);
			this.tdPepe.cambiarPin(tdPepe.getPin(), 1234);
			this.tdAna.cambiarPin(tdAna.getPin(), 1234);
		}
		catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}
	}
	
	/*Test de transferencias*/
	@Test
	public void testTransferenciaOK() {
		try {
			this.cuentaPepe.transferir(this.cuentaAna.getId(), 500, "Alquiler");
			assertTrue(this.cuentaPepe.getSaldo() == 495);
			assertTrue(this.cuentaAna.getSaldo() == 5500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testTransferenciaALaMismaCuenta() {

		try {
			cuentaPepe.transferir(1L,100, "Alquiler");
			fail("Esperaba CuentaInvalidaException");
		} catch (CuentaInvalidaException e) {
		} catch (Exception e) {
			fail("Se ha lanzado una excepción inesperada: " + e);
		}
	}
	/*FIN Test de transferencias*/
	
	/*Test compra por internet con tarjeta de crédito*/
	@Test
	public void testCompraPorInternetConTC() {
		try {
			int token = this.tcPepe.comprarPorInternet(tcPepe.getPin(), 300);
			assertTrue(this.tcPepe.getCreditoDisponible()==2000);
			this.tcPepe.confirmarCompraPorInternet(token);
			assertTrue(this.tcPepe.getCreditoDisponible()==1700);
			this.tcPepe.liquidar();
			assertTrue(this.tcPepe.getCreditoDisponible()==2000);
			assertTrue(cuentaPepe.getSaldo()==700);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testCompraPorInternetConTCCreditoInsuficiente() {
		try {
			int token = this.tcPepe.comprarPorInternet(tcPepe.getPin(), 2001);
			fail("Se esperaba - SaldoInsuficienteException");
		} catch (SaldoInsuficienteException e) {
			
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testCompraPorInternetConTCImporteIncorrecto() {
		try {
			int token = this.tcPepe.comprarPorInternet(tcPepe.getPin(), 0);
			fail("Se esperaba - ImporteInvalidoException");
		} catch (ImporteInvalidoException e) {
			
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	/*FIN Test compra por internet con tarjeta de crédito*/
	
	/*Test compra por internet con tarjeta de débito*/
	@Test
	public void testCompraPorInternetConTD() {
		try {
			int token = this.tdPepe.comprarPorInternet(tdPepe.getPin(), 300);
			this.tdPepe.confirmarCompraPorInternet(token);
			assertTrue(cuentaPepe.getSaldo()==700);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testCompraPorInternetConTDImporteIncorrecto() {
		try {
			int token = this.tdPepe.comprarPorInternet(tcPepe.getPin(), 0);
			this.tdPepe.confirmarCompraPorInternet(token);
			fail("Se esperaba - ImporteInvalidoException");
		} catch (ImporteInvalidoException e) {
			
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testCompraPorInternetConTDSaldoInsuficiente() {
		try {
			int token = this.tdPepe.comprarPorInternet(tcPepe.getPin(), 1001);
			this.tdPepe.confirmarCompraPorInternet(token);
			fail("Se esperaba - SaldoInsuficienteException");
		} catch (SaldoInsuficienteException e) {
			
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	/*FIN Test compra por internet con tarjeta de débito*/
}
