package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.enums.StatusPosicao;

import java.util.List;
import java.util.Optional;

public interface PosicaoEstoqueRepository extends JpaRepository<PosicaoEstoque, Long> {
    Optional<PosicaoEstoque> findByCodigo(String codigo);
    List<PosicaoEstoque> findByStatus(StatusPosicao status);
}
