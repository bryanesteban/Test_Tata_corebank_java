package ec.com.corebank.banquito.services.ServicesImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.corebank.banquito.ErrorManagment.CustomException;
import ec.com.corebank.banquito.models.DTO.ClienteDTO;
import ec.com.corebank.banquito.models.entities.Cliente;
import ec.com.corebank.banquito.models.entities.Persona;
import ec.com.corebank.banquito.repositories.ClienteRepository;
import ec.com.corebank.banquito.repositories.PersonaRepository;
import ec.com.corebank.banquito.services.ServInterface.ClienteInterface;

@Service
public class ClienteServiceImp implements ClienteInterface {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;


  

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
      
        List <Cliente> clientes = new ArrayList<>();
        
        clientes = clienteRepository.findByEstadoTrue(); 

        List<ClienteDTO> clientListDTO =  clientes
                                        .stream()
                                        .map(u -> ClienteDTO.build(u))
                                        .collect(Collectors.toList());
                   
                    if(clientes == null || clientes.size() == 0) {
			        throw new CustomException("Lista de clientes vacía", HttpStatus.NOT_FOUND);
		}

        return clientListDTO;
        
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findByIdClient(String clienteId) {
        
        Optional<ClienteDTO> clienteDTO;

        clienteDTO = clienteRepository
        .findByClienteid(clienteId)
        .map(u -> {
           return ClienteDTO.build(u);
        });

        if(!clienteDTO.isPresent()) {
            throw new CustomException("Error al buscar el cliente con ID " , HttpStatus.NOT_FOUND);
        }

    
        return clienteDTO;
    }

    @Override
    @Transactional
    public ClienteDTO saveClient(Cliente cliente) {

            Optional <Persona> personaValidation = personaRepository.findByIdentificacion(cliente.getIdentificacion());

            if(personaValidation.isPresent()) {
                throw new CustomException("Error el cliente ya existe: " , HttpStatus.NOT_FOUND);
            }else{
                return ClienteDTO.build(clienteRepository.save(cliente));
            }
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> update(Cliente cliente, String clienteId) {

            //Busca cliente
            Optional<Cliente> clienteOpt = clienteRepository.findByClienteid(clienteId);
            if (clienteOpt.isPresent()) {
                Cliente existingCliente = clienteOpt.get();
                // Actualiza los campos del cliente existente con los valores del nuevo cliente.
                existingCliente.setNombre(cliente.getNombre());
                existingCliente.setGenero(cliente.getGenero());
                existingCliente.setEdad(cliente.getEdad());
                existingCliente.setIdentificacion(cliente.getIdentificacion());
                existingCliente.setDireccion(cliente.getDireccion());
                existingCliente.setTelefono(cliente.getTelefono());
                existingCliente.setContrasena(cliente.getContrasena());
                existingCliente.setEstado(cliente.getEstado());
                // Guarda los cambios
                clienteRepository.save(existingCliente);
                return Optional.of(ClienteDTO.build(existingCliente));
            } else {
                throw new CustomException("Error al actualizar el cliente con ID: " + clienteId , HttpStatus.NOT_FOUND);
            }

    }

    @Override
    @Transactional
    public void remove(String clienteId) {

            Optional<Cliente> clienteOpt = clienteRepository.findByClienteid(clienteId);
            if (clienteOpt.isPresent()) {
                clienteRepository.delete(clienteOpt.get());
            } else {
                
                throw new CustomException("Cliente con ID: " + clienteId + " no encontrado.", HttpStatus.NOT_FOUND);
            }

    }
    

}
