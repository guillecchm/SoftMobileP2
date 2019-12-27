package edu.uclm.esi.iso2.banco20193capas.dao;

import org.springframework.data.repository.CrudRepository;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;

/**
 * @author Macario
 */
public interface CuentaDAO extends CrudRepository<Cuenta, Long> {

}
