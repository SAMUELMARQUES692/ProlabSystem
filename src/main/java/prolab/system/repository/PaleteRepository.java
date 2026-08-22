package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prolab.system.entity.Palete;

import java.util.List;

public interface PaleteRepository extends JpaRepository<Palete, Long> {

    @Query("SELECT p FROM Palete p JOIN FETCH p.recebimento LEFT JOIN FETCH p.residuo")
    List<Palete> findAllComRecebimento();

    @Query("SELECT p FROM Palete p JOIN FETCH p.recebimento LEFT JOIN FETCH p.residuo WHERE p.recebimento.prime = :prime")
    List<Palete> findAllPaletesPrime(@Param("prime") String prime);

}
