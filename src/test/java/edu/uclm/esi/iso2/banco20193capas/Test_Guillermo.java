package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_Guillermo extends TestCase {
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
	
	/*Test comprobar pin tarjeta*/
	
	@Test
	public void testPinOK() {
			try {
				this.tcPepe.comprobar(1234);
				assert(this.tcPepe.getPin()==1234);
			} catch (Exception e) {
				fail("No se esperaba excepción: " + e.getMessage());
			}
	}
	
	@Test
	public void testPinIncorrecto() {
			try {
				this.tcPepe.comprobar(0000);
				fail("Se esperaba PinInvalidoException");
			} catch (PinInvalidoException e) {
				
			} catch (Exception e) {
				fail("Se esperaba PinInvalidoException: " + e.getMessage());
			}
	}
	
	@Test
	public void testBloqueoDeTarjeta() {
			try {
				this.tcPepe.comprobar(0000);
			} catch (PinInvalidoException e) {
			} catch (Exception e) {
				fail("Esperaba PinInvalidoException");
			} 
			try {
				this.tcPepe.comprobar(0000);
			} catch (PinInvalidoException e) {
			} catch (Exception e) {
				fail("Esperaba PinInvalidoException");
			}
			try {
				this.tcPepe.comprobar(0000);
			} catch (PinInvalidoException e) {
			} catch (Exception e) {
				fail("Esperaba PinInvalidoException");
			}
			try {
				this.tcPepe.comprobar(1234);
			} catch (TarjetaBloqueadaException e) {
			} catch (Exception e) {
				fail("Esperaba TarjetaBloqueadaException");
			}
	}
	/*FIN Test comprobar pin tarjeta*/
	
	
	/*Test emitir tarjeta crédito*/
	@Test
	public void testEmitirTarjetaCreditoOK() {
		try {
			tcPepe = this.cuentaPepe.emitirTarjetaCredito(pepe.getNif(), 2000);
			assert(tcPepe.getTitular().getNif().equals(pepe.getNif()));
			assert(tcPepe.getCredito() == 2000);
		}catch (Exception e) {
			fail("No se esperaba ninguna excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testEmitirTarjetaCreditoClienteNoExiste() {
		try {
			this.tcPepe = this.cuentaPepe.emitirTarjetaCredito("12343Z", 2000);
			fail("Se esperaba ClienteNoEncontradoException");
		} catch (ClienteNoEncontradoException e) {
			
		}catch (Exception e) {
			fail("Se esperaba ClienteNoEncontradoException: " + e.getMessage());
		}
	}	
	
	@Test
	public void testEmitirTarjetaCreditoClienteNoAutorizado() {
		try {
			this.tcPepe = this.cuentaPepe.emitirTarjetaCredito("98765F", 2000);
			fail("Se esperaba ClienteNoAutorizadoException");
		} catch (ClienteNoAutorizadoException e) {
			
		}catch (Exception e) {
			fail("Se esperaba ClienteNoAutorizadoException: " + e.getMessage());
		}
	}	
	/*FIN Test emitir tarjeta crédito*/
	
	/*Test emitir tarjeta débito*/
	@Test
	public void testEmitirTarjetaDebitoOK() {
		try {
			tdPepe = this.cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			assert(tdPepe.getTitular().getNif().equals(pepe.getNif()));
		}catch (Exception e) {
			fail("No se esperaba ninguna excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void testEmitirTarjetaDebitoClienteNoExiste() {
		try {
			tdPepe = this.cuentaPepe.emitirTarjetaDebito("12343Z");
			fail("Se esperaba ClienteNoEncontradoException");
		} catch (ClienteNoEncontradoException e) {
			
		}catch (Exception e) {
			fail("Se esperaba ClienteNoEncontradoException: " + e.getMessage());
		}
	}	
	
	@Test
	public void testEmitirTarjetaDebitoClienteNoAutorizado() {
		try {
			tdPepe = this.cuentaPepe.emitirTarjetaDebito("98765F");
			fail("Se esperaba ClienteNoAutorizadoException");
		} catch (ClienteNoAutorizadoException e) {
			
		}catch (Exception e) {
			fail("Se esperaba ClienteNoAutorizadoException: " + e.getMessage());
		}
	}	
	/*FIN Test emitir tarjeta débito*/
	
	/*Test carga cuenta*/
	
	/*	El método tiene visibilidad private en el código y se llama desde otro 
		procedimiento como transferir, luego queda testeado con el test de transferir.
	*/
	
	/*FIN Test carga cuenta*/
}
