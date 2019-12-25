package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_Antonio extends TestCase {
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
	
	/*Test compra con tarjeta débito*/
	@Test
	public void testComprarTD() {
		
		try {
			this.tdPepe.comprar(tdPepe.getPin(), 300);
			assertTrue(cuentaPepe.getSaldo()==700);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testComprarTDSaldoInsuficiente() {
		
		try {
			
			this.tdPepe.comprar(tdPepe.getPin(), 1001);
			
		}catch(SaldoInsuficienteException e) {
		}catch (Exception e) {
			fail("Esperaba SaldoInsuficienteException");
		}
	}
	
	@Test
	public void testComprarTDImporteInvalido() {
		
		try {
			this.tdPepe.comprar(tdPepe.getPin(), -1);
			
		}catch(ImporteInvalidoException e) {
		}catch (Exception e) {
			fail("Esperaba ImporteInvalidoException");
		}
	}
	/*FIN test compra tarjeta débito*/
	
	/*Test compra tarjeta crédito*/
	@Test
	public void testComprarTC() {
		try {
			
			this.tcPepe.comprar(tcPepe.getPin(), 300);
			assertTrue(tcPepe.getCreditoDisponible()==1700);
			tcPepe.liquidar();
			assertTrue(tcPepe.getCreditoDisponible()==2000);
			assertTrue(cuentaPepe.getSaldo()==700);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testComprarTCSaldoInsuficiente() {
		try {
			
			this.tcPepe.comprar(tcPepe.getPin(), 2001);
			
		}catch (SaldoInsuficienteException e) {
		}catch (Exception e) {
			fail("Esperaba SaldoInsuficienteException");
		}
	}
	
	@Test
	public void testComprarTCImporteInvalido() {
		try {
			
			this.tcPepe.comprar(tcPepe.getPin(), -1);
			
		}catch (ImporteInvalidoException e) {
		}catch (Exception e) {
			fail("Esperaba ImporteInvalidoException");
		}
	}
	/*FIN test compra tarjeta crédito*/
	
	/*Test Ingresar Cuenta*/
	@Test
	public void testIngresarCuenta() {
		try {
			
			this.cuentaPepe.ingresar(1000);
			assertTrue(cuentaPepe.getSaldo() == 2000);
			
		}catch (Exception e) {
			fail("Esperaba ImporteInvalidoException");
		}
	}
	
	@Test
	public void testIngresarCuentaImporteInvalido() {
		try {
			
			this.cuentaPepe.ingresar(0);
			
		}catch(ImporteInvalidoException e) {
		}catch (Exception e) {
			fail("Esperaba ImporteInvalidoException");
		}
	}
	/*FIN test Ingresar Cuenta*/
}