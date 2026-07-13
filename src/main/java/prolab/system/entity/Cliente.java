package prolab.system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "documentos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250, nullable = false)
    private String razaoSocial;

    @Column(length = 18, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "telefone_contato", length = 250)
    private String contato;

    @Column(length = 500)
    private String endereco;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @OneToMany(mappedBy = "cliente")
    @Builder.Default
    private List<DocumentoCliente> documentos = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void adicionarDocumento(DocumentoCliente documento) {
        documentos.add(documento);
        documento.setCliente(this);
    }
}
