package ec.com.corebank.banquito.services.ServInterface;

import java.util.List;
import java.util.Optional;

import ec.com.corebank.banquito.models.DTO.MovimientoDTO;
import ec.com.corebank.banquito.models.entities.Movimiento;

public interface MovimientoInterface {

     List<MovimientoDTO> findAll();

    Optional<MovimientoDTO> findByidMovimiento( Long idmovimiento);
    
    MovimientoDTO saveMovimiento ( MovimientoDTO movimiento);

    Optional<MovimientoDTO> updateMovimiento(Movimiento movimiento, Long idMovimiento);

    void removeMovimiento(Long idMovimiento );

    List<MovimientoDTO> findMovimientosByFechaRange(String fechaInicio, String fechaFin);
}
