package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "tipo_residuo", length = 100, nullable = false)
    private String tipoResiduo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_destruicao")
    private TipoDeDestruicao tipoDeDestruicao;

    @Column(name = "quantidade_paletes")
    private Integer quantidadePaletes;

    @Column(name = "data_hora_prevista", nullable = false)
    private LocalDateTime dataHoraPrevista;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
