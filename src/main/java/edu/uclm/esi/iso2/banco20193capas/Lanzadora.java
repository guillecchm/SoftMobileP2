package edu.uclm.esi.iso2.banco20193capas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;

/**
 * @author Macario
 */
@SpringBootApplication
public class Lanzadora {

    /**
     * MAIN CLASS.
     * @param args argumentos para ejecutar el programa.
     * @throws Exception Excepción
     */
    public static void main(final String[] args) throws Exception {
        /**
         * @param Lanzadora.class
         * @param args
         */
        SpringApplication.run(Lanzadora.class, args);

        try {
            final Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
            pepe.insert();

            final Cuenta cuenta = new Cuenta();
            cuenta.addTitular(pepe);
            cuenta.insert();

            cuenta.ingresar(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
