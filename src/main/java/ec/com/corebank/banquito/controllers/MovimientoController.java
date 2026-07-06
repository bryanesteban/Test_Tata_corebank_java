package ec.com.corebank.banquito.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ec.com.corebank.banquito.models.DTO.MovimientoDTO;
import ec.com.corebank.banquito.models.entities.Movimiento;
import ec.com.corebank.banquito.services.ServInterface.MovimientoInterface;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(originPatterns = "*")
public class MovimientoController {


    @Autowired
    private MovimientoInterface movimientoService;

    @GetMapping
    public ResponseEntity<?> getMovimientos(){

            List<MovimientoDTO> movimientos = movimientoService.findAll();
            return new ResponseEntity<>(movimientos, HttpStatus.OK);

    }

    @GetMapping("/{idMovimientos}")
    public ResponseEntity<?> getBusquedaMovimiento(@PathVariable("idMovimientos") Long idMovimientos){

            Optional<MovimientoDTO> movimiento = movimientoService.findByidMovimiento(idMovimientos);
            return movimiento.map(ResponseEntity::ok)
                                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    

    @GetMapping("/reportes")
    public ResponseEntity<?> getMovimientosByFechaRange(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

            List<MovimientoDTO> movimientos = movimientoService.findMovimientosByFechaRange(fechaInicio, fechaFin);
            return new ResponseEntity<>(movimientos, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDTO movimiento, BindingResult result)
    {

            MovimientoDTO guardarmovimiento = movimientoService.saveMovimiento(movimiento);
            return new ResponseEntity<>(guardarmovimiento, HttpStatus.CREATED);
    }

    @PutMapping("/{idMovimientos}")
    public ResponseEntity<?> actualizarMovimiento (@PathVariable("idMovimientos") Long idMovimientos, @RequestBody Movimiento movimiento )
    {
            Optional<MovimientoDTO> updateMovimiento = movimientoService.updateMovimiento(movimiento, idMovimientos);
            return updateMovimiento.map(ResponseEntity::ok)
                                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
       
    }

    @DeleteMapping("/{idMovimientos}")
    public ResponseEntity<?> borrarMovimiento(@PathVariable("idMovimientos") Long idMovimientos)
    {

            movimientoService.removeMovimiento(idMovimientos);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
