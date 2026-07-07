package prolab.system.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prolab.system.exception.*;
import prolab.system.response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> clienteNotFound(ClienteNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "CLIENTE_NAO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AgendamentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> agendamentoNotFound(AgendamentoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "AGENDAMENTO_NAO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CaminhaoNotFoundException.class)
    public ResponseEntity<ErrorResponse> caminhaoNotFound(CaminhaoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "CAMINHAO_NAO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RecebimentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> recebimentoNotFound(RecebimentoNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "RECEBIMENTO_NAO_ENCONTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RecebimentoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> recebimentoDuplicado(RecebimentoDuplicadoException exception) {
        ErrorResponse error = new ErrorResponse(
                "RECEBIMENTO_JA_CADASTRADO",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> cnpjDuplicado(DataIntegrityViolationException exception) {
        ErrorResponse error = new ErrorResponse(
                "DADOS_DUPLICADOS",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationError(MethodArgumentNotValidException exception) {
        String mensagens = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorResponse error = new ErrorResponse(
                "DADOS_INVALIDOS",
                mensagens,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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
