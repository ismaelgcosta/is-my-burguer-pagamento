package br.com.ismyburguer.core.adapter.in;

import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERRO_AO_VALIDAR_ESTRUTURA_DE_DADOS = "Erro ao validar estrutura de dados";
    public static final String TIMESTAMP = "timestamp";

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleEntityNotFoundException(EntityNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 422
    @ExceptionHandler(BusinessException.class)
    ProblemDetail businessException(BusinessException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle(ERRO_AO_VALIDAR_ESTRUTURA_DE_DADOS);

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if(constraintViolations != null) {
            constraintViolations
                    .forEach(constraintViolation -> {
                        Path propertyPath = constraintViolation.getPropertyPath();
                        String message = constraintViolation.getMessage();
                        problemDetail.setProperty(propertyPath.toString(), message);
                    });
        } else {
            problemDetail.setDetail(e.getLocalizedMessage());
        }

        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(FeignException.class)
    ResponseEntity<Object> handleFeignExceptionBadRequest(FeignException e) throws IOException {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle(ERRO_AO_VALIDAR_ESTRUTURA_DE_DADOS);
        Optional<ByteBuffer> byteBuffer = e.responseBody();
        if( byteBuffer.isPresent()) {
            return ResponseEntity.status(e.status()).body(new ObjectMapper().readValue(byteBuffer.get().array(), JsonNode.class));
        } else {
            problemDetail.setDetail(e.getLocalizedMessage());
        }

        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle(ERRO_AO_VALIDAR_ESTRUTURA_DE_DADOS);

        List<ObjectError> constraintViolations = ex.getAllErrors();
        if(!CollectionUtils.isEmpty(constraintViolations)) {
            constraintViolations
                    .forEach(constraintViolation -> {
                        String propertyPath = FieldError.class.isAssignableFrom(constraintViolation.getClass()) ? FieldError.class.cast(constraintViolation).getField() : constraintViolation.getObjectName();
                        String message = constraintViolation.getDefaultMessage();
                        problemDetail.setProperty(propertyPath, message);
                    });
        } else {
            problemDetail.setDetail(ex.getLocalizedMessage());
        }

        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problemDetail);
    }
}