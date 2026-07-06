package ec.com.corebank.banquito.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersona")
    private Long idPersona;

    @NotBlank
    @Column(name = "nombre")
    @Size(min = 4, max = 50)
    private String nombre;


    @Column(name = "genero")
    @Size(min = 4, max = 20)
    private String genero;


    @Column(name = "edad")
    private int edad;

    @NotBlank
    @Column(name = "identificacion", unique = true)
    @Size(min = 4, max = 20)
    private String identificacion;

    @NotBlank
    @Column(name = "direccion")
    @Size(min = 4, max = 60)
    private String direccion;

    @NotBlank
    @Column(name = "telefono")
    @Size(min = 4, max = 60)
    private String telefono;

}