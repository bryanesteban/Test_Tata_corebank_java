package ec.com.corebank.banquito.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ec.com.corebank.banquito.models.DTO.CuentaDTO;
import ec.com.corebank.banquito.models.entities.Cuenta;
import ec.com.corebank.banquito.services.ServInterface.CuentaInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/cuentas")
@CrossOrigin(originPatterns = "*")
public class CuentaController {



    @Autowired
    private CuentaInterface  cuentaService;


    @GetMapping
    public ResponseEntity<?> getCuentas() {

                List<CuentaDTO> cuentas = cuentaService.findAll();
                return new ResponseEntity<>(cuentas, HttpStatus.OK);

    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<?> getBusquedaCuenta(@PathVariable("numeroCuenta") String numeroCuenta ) {

            Optional<CuentaDTO> cuenta = cuentaService.findByNCuenta(numeroCuenta);
            return cuenta.map(ResponseEntity::ok)
                            .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDTO cuentaDTO, BindingResult result) {
            CuentaDTO guardarcuenta = cuentaService.saveCuenta(cuentaDTO);
            return new ResponseEntity<>(guardarcuenta, HttpStatus.CREATED);

    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<?> actualizarCuenta(@PathVariable("numeroCuenta") String numeroCuenta, @RequestBody Cuenta cuenta) {

            Optional<CuentaDTO> updateCuenta = cuentaService.updateCuenta(cuenta, numeroCuenta);
            return updateCuenta.map(ResponseEntity::ok)
                                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<?> borrarCuenta(@PathVariable("numeroCuenta") String numeroCuenta){

            cuentaService.removeCuenta(numeroCuenta);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    


}
