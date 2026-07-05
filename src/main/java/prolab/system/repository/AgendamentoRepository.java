package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.domain.Agendamento;
import prolab.system.enums.StatusAgendamento;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByClienteId(Long clienteId);
    List<Agendamento> findByStatus(StatusAgendamento status);
}
