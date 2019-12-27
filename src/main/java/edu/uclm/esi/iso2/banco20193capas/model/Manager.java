package edu.uclm.esi.iso2.banco20193capas.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uclm.esi.iso2.banco20193capas.dao.ClienteDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.CuentaDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.MovimientoCuentaDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.MovimientoTarjetaCreditoDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.TarjetaCreditoDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.TarjetaDebitoDAO;

/**
 * El Manager da acceso a las clases DAO asociadas a las clases de dominio.
 *
 */
@Component
public final class Manager {
    /** DAO Cuenta. */
    private static CuentaDAO cuentaDAO;
    /** DAO Movimiento Cuenta. */
    private static MovimientoCuentaDAO movimientoDAO;
    /** DAO Movimiento TC. */
    private static MovimientoTarjetaCreditoDAO movimientoTCDAO;
    /** DAO Cliente. */
    private static ClienteDAO clienteDAO;
    /** DAO Tarjeta Credito. */
    private static TarjetaDebitoDAO tarjetaDebitoDAO;
    /** DAO Tarjeta Debito. */
    private static TarjetaCreditoDAO tarjetaCreditoDAO;

    /**
     * Constructor Manager.
     */
    private Manager() {
    }

    /**
     * Carga DAO.
     *
     * @param cuentaDao Gestor CuentaDAO.
     * @param movimientoDao Gestor MovimientoCuentaDAO.
     * @param clienteDao Gestor ClienteDAO.
     * @param movimientoTCDao Gestor MovimientoTarjetaCreditoDAO.
     * @param tarjetaDebitoDao Gestor TarjetaDebitoDAO.
     * @param tarjetaCreditoDao Gestor TarjetaCreditoDAO.
     */

    @Autowired
    private void loadDAO(final CuentaDAO cuentaDao,
            final MovimientoCuentaDAO movimientoDao,
            final ClienteDAO clienteDao,
            final MovimientoTarjetaCreditoDAO movimientoTCDao,
            final TarjetaDebitoDAO tarjetaDebitoDao,
            final TarjetaCreditoDAO tarjetaCreditoDao) {
        Manager.cuentaDAO = cuentaDao;
        Manager.movimientoDAO = movimientoDao;
        Manager.clienteDAO = clienteDao;
        Manager.movimientoTCDAO = movimientoTCDao;
        Manager.tarjetaDebitoDAO = tarjetaDebitoDao;
        Manager.tarjetaCreditoDAO = tarjetaCreditoDao;
    }

    /**
     * Obtener cuenta.
     *
     * @return cuentaDAO.
     */
    public static CuentaDAO getCuentaDAO() {
        return cuentaDAO;
    }

    /**
     * Obtener Movimiento.
     *
     * @return movimientoDAO.
     */
    public static MovimientoCuentaDAO getMovimientoDAO() {
        return movimientoDAO;
    }

    /**
     * Obtener Cliente.
     *
     * @return clienteDAO.
     */
    public static ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    /**
     * Obtener MovimientoTC.
     *
     * @return movimientoTCDAO.
     */
    public static MovimientoTarjetaCreditoDAO getMovimientoTarjetaCreditoDAO() {
        return movimientoTCDAO;
    }

    /**
     * Obtener TD.
     *
     * @return tarjetaDebitoDAO.
     */
    public static TarjetaDebitoDAO getTarjetaDebitoDAO() {
        return tarjetaDebitoDAO;
    }

    /**
     * Obtener TC.
     *
     * @return tarjetaCreditoDAO.
     */
    public static TarjetaCreditoDAO getTarjetaCreditoDAO() {
        return tarjetaCreditoDAO;
    }
}
