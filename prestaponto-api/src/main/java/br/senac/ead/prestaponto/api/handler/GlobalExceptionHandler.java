package br.senac.ead.prestaponto.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.senac.ead.prestaponto.api.exception.EntityAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(403);
        problemDetail.setTitle("Access Denied");
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(409);
        problemDetail.setTitle("Conflict");
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.status(409).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.");
        log.error(ex.getMessage());
        
        return ResponseEntity.internalServerError().body(problemDetail);
    }
}
