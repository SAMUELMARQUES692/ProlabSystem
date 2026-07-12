package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.domain.PosicaoEstoque;

import java.util.Optional;

public interface PosicaoEstoqueRepository extends JpaRepository<PosicaoEstoque, Long> {
    Optional<PosicaoEstoque> findByCodigo(String codigo);
}
