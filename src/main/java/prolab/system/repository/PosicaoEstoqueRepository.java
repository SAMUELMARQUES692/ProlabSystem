package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.domain.PosicaoEstoque;

public interface PosicaoEstoqueRepository extends JpaRepository<PosicaoEstoque, Long> {
}
