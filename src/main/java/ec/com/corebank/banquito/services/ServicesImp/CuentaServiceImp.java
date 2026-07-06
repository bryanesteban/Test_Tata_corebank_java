package ec.com.corebank.banquito.services.ServicesImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.corebank.banquito.ErrorManagment.CustomException;
import ec.com.corebank.banquito.ErrorManagment.ResourceNotFoundException;
import ec.com.corebank.banquito.models.DTO.CuentaDTO;
import ec.com.corebank.banquito.models.entities.Cliente;
import ec.com.corebank.banquito.models.entities.Cuenta;
import ec.com.corebank.banquito.repositories.ClienteRepository;
import ec.com.corebank.banquito.repositories.CuentaRespository;
import ec.com.corebank.banquito.services.ServInterface.CuentaInterface;


@Service
public class CuentaServiceImp implements CuentaInterface {

    @Autowired
    private CuentaRespository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;



    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> findAll() {
        
        List<Cuenta> cuentas = new ArrayList<>();

        cuentas = (List<Cuenta>) cuentaRepository.findAll();
        
           
        if(cuentas == null || cuentas.size() ==0)
        {
            throw new CustomException("Error al buscar todas las cuentas", HttpStatus.NOT_FOUND);
        }

        return cuentas
            .stream()
            .map(u -> CuentaDTO.build(u))
            .collect(Collectors.toList());
    
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaDTO> findByNCuenta(String idCuenta) {
      
        Optional<CuentaDTO> cuentaDTO;

        cuentaDTO = cuentaRepository
                        .findByNumerocuenta(idCuenta)
                        .map(CuentaDTO::build);

        if(!cuentaDTO.isPresent()){
            throw new CustomException("Error al buscar la cuenta con ID: " + idCuenta, HttpStatus.NOT_FOUND);
        }

        return cuentaDTO;
    }

    @Override
    @Transactional
    public CuentaDTO saveCuenta(CuentaDTO cuentaDTO) {
        
            Optional <Cuenta> cuentaValidacion = cuentaRepository.findByNumerocuenta(cuentaDTO.getNumeroCuenta());
            
            Cliente cliente = clienteRepository.findByClienteid(cuentaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
            


            if(!cuentaValidacion.isPresent()){
                Cuenta cuenta = new Cuenta();
                cuenta.setNumerocuenta(cuentaDTO.getNumeroCuenta());
                cuenta.setCliente(cliente);
                cuenta.setEstado(cuentaDTO.isEstado());
                cuenta.setSaldo(cuentaDTO.getSaldo());
                cuenta.setTipocuenta(cuentaDTO.getTipoCuenta());


                return CuentaDTO.build(cuentaRepository.save(cuenta));
            }else{
                throw new CustomException("Error, ya existe la cuenta ", HttpStatus.NOT_FOUND );
            }


            // Manejar otras excepciones
            
        
    }

    @Override
    @Transactional
    public Optional<CuentaDTO> updateCuenta(Cuenta cuenta, String numeroCuenta) {

            Optional<Cuenta> verificaCuenta = cuentaRepository.findByNumerocuenta(numeroCuenta);
            
            if(verificaCuenta.isPresent()){
                Cuenta cuentaDb = verificaCuenta.get();

                cuentaDb.setTipocuenta(cuenta.getTipocuenta());
                cuentaDb.setSaldo(cuenta.getSaldo());
                cuentaDb.setEstado(cuenta.isEstado());


                cuentaRepository.save(cuentaDb);
                
                return Optional.of(CuentaDTO.build(cuentaDb));
            }else {
                throw new CustomException("Error al actualizar la cuenta con numero:"+ numeroCuenta, HttpStatus.NOT_FOUND );
            }
    }  

    @Override
    @Transactional
    public void removeCuenta(String numeroCuenta) {
        
            Optional<Cuenta> verificaCuenta = cuentaRepository.findByNumerocuenta(numeroCuenta);
            if(verificaCuenta.isPresent()){
                cuentaRepository.delete(verificaCuenta.get());
            }else{
                    throw new CustomException("Cuenta con numero:"+ numeroCuenta+"no encontrada!", HttpStatus.NOT_FOUND);
            }
       
    }

    
    
   




}
