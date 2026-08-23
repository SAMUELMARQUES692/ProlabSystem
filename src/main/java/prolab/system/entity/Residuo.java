package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prolab.system.enums.StatusResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "residuos")
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"palete", "posicaoEstoque"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posicao_id", nullable = false, unique = true)
    private PosicaoEstoque posicaoEstoque;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "palete_id", nullable = false, unique = true)
    private Palete palete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusResiduo status;

    @Column(name = "mtr_vinculado", length = 355)
    private String mtrVinculado;

    @Column(name = "data_destinacao")
    private LocalDateTime dataDestinacao;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
