package ec.com.corebank.banquito.models.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="cuenta")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {


    @Id
    @NotBlank
    @Column(name = "numerocuenta", nullable = false, length = 30)
    private String numerocuenta;

    @NotBlank
    @Column(name = "tipocuenta")
    @Size(min = 4, max = 20)
    private String tipocuenta;

    @Column(name = "saldo")
    private String saldo;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clienteid", nullable = false, referencedColumnName = "clienteid")
    private Cliente cliente;


}
