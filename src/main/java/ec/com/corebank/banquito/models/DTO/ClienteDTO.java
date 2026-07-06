package ec.com.corebank.banquito.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import ec.com.corebank.banquito.models.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos para el cliente")
public class ClienteDTO {



    

    private String identificacion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;

    
    public static ClienteDTO build(Cliente cliente){

        if( cliente == null){
            throw new RuntimeException("Debe enviar el entity Cliente!");
        }

        return  ClienteDTO.builder()
                        .identificacion(cliente.getIdentificacion())
                        .nombre(cliente.getNombre())
                        .direccion(cliente.getDireccion())
                        .telefono(cliente.getTelefono())
                        .estado(cliente.getEstado())
                        .build();
                                               

    }

    

}
