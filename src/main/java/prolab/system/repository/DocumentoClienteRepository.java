package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.domain.DocumentoCliente;

import java.util.List;

public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {

    List<DocumentoCliente> findByClienteId(Long clienteId);
}
