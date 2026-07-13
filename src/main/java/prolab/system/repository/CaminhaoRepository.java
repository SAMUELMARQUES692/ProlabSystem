package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.entity.Caminhao;

import java.util.Optional;

public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {

    Optional<Caminhao> findByPlaca(String placa);
}
