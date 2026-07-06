package ec.com.corebank.banquito.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ec.com.corebank.banquito.models.entities.Persona;

public interface PersonaRepository 
    extends CrudRepository<Persona,Long>{

        Optional<Persona> findByIdentificacion(String identificacion);
}
