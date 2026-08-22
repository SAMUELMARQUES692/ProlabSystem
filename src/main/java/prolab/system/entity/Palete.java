package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paletes")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"recebimento", "residuo"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Palete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticket;

    @Column(name = "numero_palete", nullable = false)
    private Integer numeroPalete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoResiduo tipo;

    @Column(nullable = false)
    private BigDecimal peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_fisico", nullable = false)
    private EstadoFisico estadoFisico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recebimento_id", nullable = false)
    private Recebimento recebimento;

    @OneToOne(mappedBy = "palete", fetch = FetchType.LAZY)
    private Residuo residuo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
