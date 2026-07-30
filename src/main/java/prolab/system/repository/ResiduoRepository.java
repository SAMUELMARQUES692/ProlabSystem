package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    List<Residuo> findByStatus(StatusResiduo status);
    List<Residuo> findByTipoResiduo(String tipoResiduo);
    List<Residuo> findByPosicaoEstoqueId(Long posicaoId);
    List<Residuo> findByPosicaoEstoqueCodigo(String codigo);

    @Query("SELECT COALESCE(SUM(r.quantidade), 0) FROM Residuo r WHERE r.posicaoEstoque.id = :posicaoId")
    BigDecimal somarQuantidadePorPosicao(@Param("posicaoId") Long posicaoId);
}
