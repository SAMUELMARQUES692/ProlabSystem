package prolab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import prolab.system.entity.ControleSequecialTicket;

public interface ControleSequecialTicketRepository extends JpaRepository<ControleSequecialTicket, Long> {

    @Transactional
    @Query(value = """
        INSERT INTO  controle_sequencial_ticket (ano, ultimo_numero)
        VALUES (:ano, 1)
        ON CONFLICT (ano)
        DO UPDATE SET ultimo_numero = controle_sequencial_ticket.ultimo_numero + 1
        RETURNING ultimo_numero
        """, nativeQuery = true)
    Integer proximoNumero(@Param("ano") Integer ano);
}
