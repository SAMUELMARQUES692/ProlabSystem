package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;

import java.util.List;
import java.util.Optional;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.status = :status")
    List<Residuo> findByStatus(@Param("status")StatusResiduo status);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE p.tipo = :tipo")
    List<Residuo> findByPaleteTipo(@Param("tipo")TipoResiduo tipo);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.posicaoEstoque.id = :posicaoId AND r.status <> 'DESTRUIDO'")
    Optional<Residuo> findAtivoByPosicaoEstoqueId(@Param("posicaoId") Long posicaoId);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento WHERE r.posicaoEstoque.codigo = :codigo")
    List<Residuo> findByPosicaoEstoqueCodigo(@Param("codigo")String codigo);

    @Query("SELECT r FROM Residuo r JOIN FETCH r.palete p JOIN FETCH p.recebimento JOIN FETCH r.posicaoEstoque")
    List<Residuo> findAllComPaleteRecebimento();

    boolean existsByPosicaoEstoqueIdAndStatusNot(Long posicaoId, StatusResiduo status);


}
