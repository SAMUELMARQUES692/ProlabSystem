package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prolab.system.enums.StatusPosicao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posicoes_estoque")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "residuos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PosicaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "capacidade")
    private BigDecimal capacidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPosicao status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "posicaoEstoque", fetch = FetchType.LAZY)
    private Residuo residuo;
}
