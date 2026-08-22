package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;
import java.util.List;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.status = :status")
    List<Residuo> findByStatus(@Param("status")StatusResiduo status);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE p.tipo = :tipo")
    List<Residuo> findByPaleteTipo(@Param("tipo")TipoResiduo tipo);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.posicaoEstoque.id = :posicaoId")
    List<Residuo> findByPosicaoEstoqueId(@Param("posicaoId")Long posicaoId);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.posicaoEstoque.codigo = :codigo")
    List<Residuo> findByPosicaoEstoqueCodigo(@Param("codigo")String codigo);

    @Query("SELECT COALESCE(SUM(r.palete.peso), 0) FROM Residuo r WHERE r.posicaoEstoque.id = :posicaoId")
    BigDecimal somarPesoPorPosicao(@Param("posicaoId") Long posicaoId);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.status = :status")
    List<Residuo> findByStatusComPaleteRecebimento(@Param("status") StatusResiduo status);


}
