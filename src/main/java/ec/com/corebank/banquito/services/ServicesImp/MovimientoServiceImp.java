package ec.com.corebank.banquito.services.ServicesImp;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.corebank.banquito.ErrorManagment.CustomException;
import ec.com.corebank.banquito.ErrorManagment.ResourceNotFoundException;
import ec.com.corebank.banquito.Util.transaccionTipo;
import ec.com.corebank.banquito.models.DTO.MovimientoDTO;
import ec.com.corebank.banquito.models.entities.Cliente;
import ec.com.corebank.banquito.models.entities.Cuenta;
import ec.com.corebank.banquito.models.entities.Movimiento;
import ec.com.corebank.banquito.repositories.CuentaRespository;
import ec.com.corebank.banquito.repositories.MovimientoRepository;
import ec.com.corebank.banquito.services.ServInterface.MovimientoInterface;

@Service
public class MovimientoServiceImp implements MovimientoInterface {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRespository cuentaRespository;



    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> findAll() {
       
        List<Movimiento> movimientosLista = new ArrayList<>();
        List <MovimientoDTO> movimientos = new ArrayList<>();



            movimientosLista = (List<Movimiento>) movimientoRepository.findAll();

            if(movimientosLista == null || movimientosLista.size() == 0)
            {
                throw new CustomException("Error al devolver los movimientos: ", HttpStatus.NOT_FOUND);
            }

            movimientos = movimientosLista.stream().map(
            movimiento -> {
                Cuenta cuenta = movimiento.getCuenta();
                Cliente cliente  = cuenta.getCliente();
               
                return MovimientoDTO.build(cliente, cuenta, movimiento);
            }
           ).collect(Collectors.toList());


        return movimientos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientoDTO> findByidMovimiento(Long idmovimiento) {
        
        MovimientoDTO movimientoDTO = null;

            
            Optional<Movimiento> optionalMovimiento = movimientoRepository.findByIdmovimiento(idmovimiento);

            if( optionalMovimiento.isPresent()){
                
                Movimiento movimientoConsulta = optionalMovimiento.get();
    
                if(movimientoConsulta.getCuenta() == null){
                    throw new ResourceNotFoundException("No se encontró una cuenta asociada al movimiento ID: " + idmovimiento);
                }

                Cuenta cuenta = movimientoConsulta.getCuenta();
                Cliente cliente = cuenta.getCliente();

                movimientoDTO = MovimientoDTO.build(cliente, cuenta, movimientoConsulta);

                return Optional.of(movimientoDTO);
            }else {
                throw new CustomException("Ocurrió un error al obtener el movimiento ", HttpStatus.NOT_FOUND );
            }


       

    }


    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> findMovimientosByFechaRange(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(fechaInicio, formatter);
        LocalDate endDate = LocalDate.parse(fechaFin, formatter);
        
        List<Movimiento> movimientosLista = movimientoRepository.findByFechamovimientoBetween(startDate, endDate);
        
        if(movimientosLista == null || movimientosLista.size() == 0)
            {
                throw new CustomException("No se encontraron movimientos", HttpStatus.NOT_FOUND );
            }

        return movimientosLista.stream()
            .map(movimiento -> {
                Cuenta cuenta = movimiento.getCuenta();
                Cliente cliente = cuenta.getCliente();
                return MovimientoDTO.build(cliente, cuenta, movimiento);
            })
            .collect(Collectors.toList());
    }
    

    @Override
    @Transactional
    public MovimientoDTO saveMovimiento(MovimientoDTO movimiento) {

        Cuenta cuenta = cuentaRespository.findByNumerocuenta(movimiento.getNumerocuenta())
                .orElseThrow(() -> new CustomException("Cuenta no encontrada", HttpStatus.NOT_FOUND));

        Cliente cliente = cuenta.getCliente();

        transaccionTipo tipo = transaccionTipo.valueOf(movimiento.getTipomovimiento().toString());

        Long saldoActual = Long.parseLong(cuenta.getSaldo());
        Long valorMovimiento = Long.parseLong(movimiento.getValor());

        Long nuevoSaldo = saldoActual + (tipo.getFormula() * valorMovimiento);

        if (nuevoSaldo < 0) {
            throw new CustomException("Saldo insuficiente para realizar la operación.", HttpStatus.BAD_REQUEST);
        }


        cuenta.setSaldo(String.valueOf(nuevoSaldo));
        cuentaRespository.save(cuenta);


        Movimiento newMovimiento = new Movimiento();
        newMovimiento.setFechamovimiento(LocalDate.now());
        newMovimiento.setCuenta(cuenta);
        newMovimiento.setTipomovimiento(movimiento.getTipomovimiento());
        newMovimiento.setSaldoInicial(saldoActual.toString());
        newMovimiento.setSaldo(String.valueOf(nuevoSaldo));
        newMovimiento.setValor(String.valueOf(Long.parseLong(movimiento.getValor())*tipo.getFormula()));

        Movimiento saved = movimientoRepository.save(newMovimiento);

        return MovimientoDTO.build(cliente, cuenta, saved);
    }

    @Override
    @Transactional
    public Optional<MovimientoDTO> updateMovimiento(Movimiento movimiento, Long idMovimiento) {
        
        Optional<MovimientoDTO> movimientoResultado = null;
        
            Optional<Movimiento> verificaMovimiento = movimientoRepository.findByIdmovimiento(idMovimiento);

            if(verificaMovimiento.isPresent()){
                Movimiento movimientobd = verificaMovimiento.get();
                Cuenta cuentabd = movimientobd.getCuenta();
                Cliente clientebd = cuentabd.getCliente();
                movimientobd.setTipomovimiento(movimiento.getTipomovimiento());

                Long saldoMovimiento = Long.valueOf(String.valueOf(cuentabd.getSaldo())) + Long.valueOf(movimiento.getSaldo());
                movimientobd.setSaldo(String.valueOf(saldoMovimiento));
                movimientobd.setValor(movimiento.getValor());
                Movimiento movimientoagregado = movimientoRepository.save(movimientobd);
                movimientoResultado = Optional.of(MovimientoDTO.build(clientebd, cuentabd, movimientoagregado));

                return movimientoResultado;
            }else {
                throw new CustomException("Error al actualizar el Movimiento con numero:"+ idMovimiento, HttpStatus.NOT_FOUND);
            }



    }

    @Override
    @Transactional
    public void removeMovimiento(Long idMovimiento) {
        
            Optional<Movimiento> verificaMovimiento = movimientoRepository.findByIdmovimiento(idMovimiento);

            if(verificaMovimiento.isPresent()){
                movimientoRepository.delete(verificaMovimiento.get());
            }else{
                    throw new RuntimeException("Movimiento con numero: "+idMovimiento+",no encontrada!");
            }

    }



    
}
