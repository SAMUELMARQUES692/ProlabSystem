package prolab.system.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PosicaoEstoqueResponse(
   Long id,
   String codigo,
   BigDecimal capacidade,
   LocalDateTime createdAt
) {}
