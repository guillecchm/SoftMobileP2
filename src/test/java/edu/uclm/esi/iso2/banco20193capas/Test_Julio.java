package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest

public class Test_Julio extends TestCase {
	
	private Cuenta cuentaPepe;
	private Cliente pepe;
	private TarjetaDebito tdPepe;
	private TarjetaCredito tcPepe;
	
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		this.pepe = new Cliente("12345X", "Pepe", "Pérez"); this.pepe.insert();
		
		this.cuentaPepe = new Cuenta(1);
		try {
			this.cuentaPepe.addTitular(pepe); this.cuentaPepe.insert(); this.cuentaPepe.ingresar(1000);
		
			this.tcPepe = this.cuentaPepe.emitirTarjetaCredito(pepe.getNif(), 2000);
			this.tdPepe = this.cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			
			this.tcPepe.cambiarPin(tcPepe.getPin(), 1234);
			this.tdPepe.cambiarPin(tdPepe.getPin(), 1234);
			
			
		}
		catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}
	}
	
	
	@Test
	public void testCambiarPinTcredito() {
		try {
			this.tcPepe.cambiarPin(1234,1235);
			assertTrue(this.tcPepe.getPin()==1235);
		} catch (Exception e) {
			fail("No se espera excepción: " + e.getMessage());
		} 
	}
	
	@Test
	public void testCambiarPinTcreditoIncorrecto() {
		try {
			this.tcPepe.cambiarPin(0000, 1334);
			fail("Se esperaba PinInvalidoException");
		} catch (PinInvalidoException e) {
			
		} catch (Exception e) {
			fail("Se esperaba PinInvalidoException: " + e.getMessage());
		}
	}
	
	@Test
	public void testCambiarPinTdebito() {
		try {
			this.tcPepe.cambiarPin(1234,1235);
			assertTrue(this.tcPepe.getPin()==1235);
		} catch (Exception e) {
			fail("No se espera excepción: " + e.getMessage());
		} 
	}
	
	@Test
	public void testCambiarPinTdebitoIncorrecto() {
		try {
			this.tdPepe.cambiarPin(0000, 1334);
			fail("Se esperaba PinInvalidoException");
		} catch (PinInvalidoException e) {
			
		} catch (Exception e) {
			fail("Se esperaba PinInvalidoException: " + e.getMessage());
		}
	}
	
	
	@Test
	public void testRetirarImporteCorrecto() {
			try {
			this.cuentaPepe.retirar(200, "retirar");
			assert(this.cuentaPepe.getSaldo()==800);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		} 
	
	}
	
	@Test
	public void testRetirarImporteNulo() {
			try {
			this.cuentaPepe.retirar(-200, "retirar");
			fail("Se esperaba ImporteInvalidoException");	
		} catch (ImporteInvalidoException e) {
			
		} catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException: " + e.getMessage());
		} 
	
	}
	
	@Test
	public void testRetirarSaldoInsuficiente() {
		try {
		this.cuentaPepe.retirar(2200, "retirar");
		fail("Se esperaba SaldoInsuficienteException");
	} catch (SaldoInsuficienteException e) {
		
	} catch (Exception e) {
		fail("Se esperaba SaldoInsuficienteException: " + e.getMessage());
	} 
}
}
