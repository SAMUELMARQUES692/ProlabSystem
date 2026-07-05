package prolab.system.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "caminhoes")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String placa;

    @Column(length = 50)
    private String modelo;

    @Column(length = 50, nullable = false)
    private String motorista;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}