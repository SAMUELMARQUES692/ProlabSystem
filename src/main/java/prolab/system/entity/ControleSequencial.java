package prolab.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "controle_sequencial")
@Getter
@Setter
@EqualsAndHashCode(of = "ano")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ControleSequencial {

    @Id
    private Integer ano;

    @Column(name = "ultimo_numero", nullable = false)
    private Integer ultimoNumero;
}
