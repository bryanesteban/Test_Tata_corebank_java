package ec.com.corebank.banquito.controllers;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ec.com.corebank.banquito.models.DTO.ClienteDTO;
import ec.com.corebank.banquito.models.entities.Cliente;
import ec.com.corebank.banquito.services.ServInterface.ClienteInterface;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(originPatterns = "*")
public class ClienteController {

    @Autowired
    private ClienteInterface clienteService;

    @GetMapping
    public ResponseEntity<?> getAllClientes() {


            List<ClienteDTO> clientes = clienteService.findAll();
            return new ResponseEntity<>(clientes, HttpStatus.OK);

    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getClienteById(@PathVariable("clienteId") String clienteId) {

            Optional<ClienteDTO> cliente = clienteService.findByIdClient(clienteId);
            return cliente.map(ResponseEntity::ok)
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente, BindingResult result) {


            ClienteDTO savedCliente = clienteService.saveClient(cliente);
            return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);


    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<?> actualizarCliente(@PathVariable("clienteId") String clienteId, 
                                                     @RequestBody Cliente cliente) {

            Optional<ClienteDTO> updatedCliente = clienteService.update(cliente, clienteId);
            return updatedCliente.map(ResponseEntity::ok)
                                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<?> borrarCliente(@PathVariable("clienteId") String clienteId) {
            clienteService.remove(clienteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}