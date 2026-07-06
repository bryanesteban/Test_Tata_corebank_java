package ec.com.corebank.banquito.services.ServicesImp;

import ec.com.corebank.banquito.models.DTO.CuentaDTO;
import ec.com.corebank.banquito.models.entities.Cliente;
import ec.com.corebank.banquito.models.entities.Cuenta;
import ec.com.corebank.banquito.models.entities.Persona;
import ec.com.corebank.banquito.repositories.ClienteRepository;
import ec.com.corebank.banquito.repositories.CuentaRespository;
import ec.com.corebank.banquito.services.ServicesImp.CuentaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CuentaServiceIntegrationTest {

    @Autowired
    private CuentaServiceImp cuentaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRespository cuentaRepository;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        // Crear y guardar un cliente en la base de datos para las pruebas
        Persona persona = Persona.builder()
                .nombre("Hades Polydegmon")
                .genero("masculino")
                .edad(34)
                .identificacion("1748395839")
                .direccion("Campos Eliseos")
                .telefono("535-1234")
                .build();

        cliente = Cliente.builder()
                .clienteid("100100")
                .contrasena("securePassword")
                .estado(true)
                .persona(persona)
                .build();
        clienteRepository.save(cliente);
        // Limpiar la base de datos antes de cada prueba si es necesario
        //cuentaRepository.deleteAll();
    }



    @Test
    @Transactional
    public void testSaveAndFindCuenta() {
        // Crear un DTO de cuenta para prueba
        CuentaDTO cuentaDTO = new CuentaDTO("643612", cliente.getClienteid(), "AHORRO", "1000", true);

        // Guardar la cuenta
        CuentaDTO savedCuenta = cuentaService.saveCuenta(cuentaDTO);

        // Verificar que la cuenta se guardó correctamente
        assertNotNull(savedCuenta);
        assertEquals(cuentaDTO.getNumeroCuenta(), savedCuenta.getNumeroCuenta());

        // Buscar la cuenta
        Optional<CuentaDTO> foundCuenta = cuentaService.findByNCuenta(cuentaDTO.getNumeroCuenta());

        // Verificar los resultados
        assertTrue(foundCuenta.isPresent());
        assertEquals(cuentaDTO.getNumeroCuenta(), foundCuenta.get().getNumeroCuenta());
    }

    

   
}
