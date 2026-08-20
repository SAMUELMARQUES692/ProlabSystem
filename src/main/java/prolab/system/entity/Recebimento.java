package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "recebimento")
    private List<Palete> paletes;

    @Column(length = 20, unique = true, nullable = false)
    private String prime;

    @Column(name = "quantidade_paletes", nullable = false)
    @Builder.Default
    private Integer quantidadePaletes = 0;

    @Column(name = "data_hora_recebimento", nullable = false)
    private LocalDateTime dataHoraRecebimento;

    @Column(name = "peso_conferido", nullable = false)
    @Builder.Default
    private BigDecimal pesoConferido = BigDecimal.ZERO;

    @Column(length = 500)
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
