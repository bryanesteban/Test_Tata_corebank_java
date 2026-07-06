package ec.com.corebank.banquito.models.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="Movimientos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmovimiento;

    @Column(name = "fechamovimiento")
    private LocalDate fechamovimiento;

    @NotBlank
    @Column(name = "tipomovimiento")
    @Size(min = 4, max = 30)
    private String tipomovimiento;

    @NotBlank
    @Column(name = "valor")
    private String valor;

    @NotBlank
    @Column(name = "saldo")
    @Size(min = 1, max = 30)
    private String saldo;

    @NotBlank
    @Column(name = "saldoinicial")
    @Size(min = 1, max = 30)
    private String saldoInicial;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "numerocuenta", nullable = false, referencedColumnName = "numerocuenta")
    private Cuenta cuenta;

}
