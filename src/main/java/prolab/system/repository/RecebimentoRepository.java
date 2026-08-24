package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prolab.system.entity.Recebimento;

import java.util.List;
import java.util.Optional;

public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {

    Optional<Recebimento> findByAgendamentoId(Long agendamentoId);

    @Query("SELECT r FROM Recebimento r JOIN FETCH r.cliente JOIN FETCH r.caminhao JOIN FETCH r.agendamento WHERE r.prime = :prime")
    Optional<Recebimento> findByPrime(@Param("prime") String prime);

    @Query("SELECT r FROM Recebimento r JOIN FETCH r.cliente JOIN FETCH r.caminhao JOIN FETCH r.agendamento")
    List<Recebimento> findAllComRelacionamentos();
}
