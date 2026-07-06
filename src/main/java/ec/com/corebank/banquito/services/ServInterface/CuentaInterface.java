package ec.com.corebank.banquito.services.ServInterface;

import java.util.List;
import java.util.Optional;

import ec.com.corebank.banquito.models.DTO.CuentaDTO;
import ec.com.corebank.banquito.models.entities.Cuenta;

public interface CuentaInterface {


    List<CuentaDTO> findAll();

    Optional<CuentaDTO> findByNCuenta( String idCuenta);
    
    CuentaDTO saveCuenta ( CuentaDTO cuenta);

    Optional<CuentaDTO> updateCuenta(Cuenta cuenta, String numeroCuenta);

    void removeCuenta(String numeroCuenta );
}
