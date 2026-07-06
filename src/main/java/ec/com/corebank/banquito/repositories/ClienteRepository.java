package ec.com.corebank.banquito.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ec.com.corebank.banquito.models.entities.Cliente;

public interface ClienteRepository 
    extends CrudRepository<Cliente,Long>{

        List <Cliente> findByEstadoTrue();
        Optional<Cliente>  findByClienteid(String clienteid);
        

}
