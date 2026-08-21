package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prolab.system.entity.Palete;

import java.util.List;
import java.util.Optional;

public interface PaleteRepository extends JpaRepository<Palete, Long> {

    @Query("SELECT p FROM Palete p JOIN FETCH p.recebimento")
    List<Palete> findAllComRecebimento();

    @Query("SELECT p FROM Palete p JOIN FETCH p.recebimento")
    List<Palete> findAllPaletesPrime(String prime);

}
