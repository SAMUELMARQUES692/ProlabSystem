package prolab.system.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import prolab.system.exception.*;
import prolab.system.response.ErrorResponse;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> clienteNotFound(ClienteNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "CLIENTE_NÃO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AgendamentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> agendamentoNotFound(AgendamentoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "AGENDAMENTO_NÃO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CaminhaoNotFoundException.class)
    public ResponseEntity<ErrorResponse> caminhaoNotFound(CaminhaoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "CAMINHÃO_NÃO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RecebimentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> recebimentoNotFound(RecebimentoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "RECEBIMENTO_NÃO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AgendamentoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> agendamentoDuplicado(AgendamentoDuplicadoException exception) {
        ErrorResponse error = new ErrorResponse(
                "AGENDAMENTO_JÁ_CADASTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    // Fallback para erros inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error: ", ex); // adicione isso
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
