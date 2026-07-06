package ec.com.corebank.banquito.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Cliente")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Persona {

    @NotBlank
    @Column(name = "clienteid", unique = true)
    @Size(min = 4, max = 20)
    private String clienteid;

    @NotBlank
    @Column(name = "contrasena")
    @Size(min = 4, max = 30)
    private String contrasena;


    @Column(name = "estado")
    private Boolean estado;

    @OneToOne
    @JoinColumn(name = "IDPERSONA", referencedColumnName = "IDPERSONA")
    private Persona persona;

    
  
}