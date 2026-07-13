package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prolab.system.enums.TipoDocumento;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos_clientes")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"cliente", "recebimento"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recebimento_id")
    private Recebimento recebimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDocumento tipo;

    @Column(length = 50)
    private String numero;

    @Column(name = "arquivo_url", length = 500)
    private String arquivoUrl;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(length = 500)
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}