package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.domain.Recebimento;

import java.util.Optional;

public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {

    Optional<Recebimento> findByAgendamentoId(Long agendamentoId);

}
