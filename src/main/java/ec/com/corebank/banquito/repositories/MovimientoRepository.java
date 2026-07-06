package ec.com.corebank.banquito.repositories;


import org.springframework.data.repository.CrudRepository;



import ec.com.corebank.banquito.models.entities.Movimiento;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

public interface MovimientoRepository
    extends CrudRepository<Movimiento,Long> {

        Optional<Movimiento> findByIdmovimiento(Long idMovimiento);

        List<Movimiento> findByFechamovimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
