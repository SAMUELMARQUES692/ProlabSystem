package prolab.system.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recebimentos")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"caminhao", "agendamento", "cliente"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recebimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id", nullable = false, unique = true)
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @Column(length = 20, unique = true, nullable = false)
    private String prime;

    @Column(name = "data_hora_recebimento", nullable = false)
    private LocalDateTime dataHoraRecebimento;

    @Column(name = "peso_conferido")
    private BigDecimal pesoConferido;

    @Column(length = 500)
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
