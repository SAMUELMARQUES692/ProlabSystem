package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;

import java.util.List;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    List<Residuo> findByStatus(StatusResiduo status);
}
