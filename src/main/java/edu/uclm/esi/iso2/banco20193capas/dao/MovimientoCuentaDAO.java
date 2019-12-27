package edu.uclm.esi.iso2.banco20193capas.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.uclm.esi.iso2.banco20193capas.model.MovimientoCuenta;

/**
 *
 * @author Macario
 *
 */
public interface MovimientoCuentaDAO
        extends CrudRepository<MovimientoCuenta, Long> {
    /**
     *
     * @param identification id de la cuenta que se quiere recuperar.
     * @return la lista de los movimientos de la cuenta.
     */
     List<MovimientoCuenta> findByCuentaId(Long identification);
}