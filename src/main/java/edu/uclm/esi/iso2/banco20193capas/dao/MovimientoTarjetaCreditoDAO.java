package edu.uclm.esi.iso2.banco20193capas.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.uclm.esi.iso2.banco20193capas.model.MovimientoTarjetaCredito;

/**
 * .
 *
 * Esta clase
 *
 * @author: julio
 *
 * @version: 22/09/2019/A
 */

public interface MovimientoTarjetaCreditoDAO
        /**
         *
         */
        extends CrudRepository<MovimientoTarjetaCredito, Long> {

    /**
     *@param id is
     *@return id
     *
     */
    List<MovimientoTarjetaCredito> findByTarjetaId(Long id);
}