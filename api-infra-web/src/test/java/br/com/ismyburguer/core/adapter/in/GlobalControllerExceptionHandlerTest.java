package br.com.ismyburguer.core.adapter.in;

import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalControllerExceptionHandlerTest {

    @InjectMocks
    private GlobalControllerExceptionHandler manipuladorExcecoes;

    @Mock
    private WebRequest requisicaoWeb;

    @Test
    void manipularExcecaoEntidadeNaoEncontrada_DeveRetornarRespostaNaoEncontrada() {
        // Arrange
        EntityNotFoundException excecao = new EntityNotFoundException("Entidade não encontrada");

        // Act
        ProblemDetail respostaEntidade = manipuladorExcecoes.handleEntityNotFoundException(excecao);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), respostaEntidade.getStatus());
    }

    @Test
    void manipularExcecaoNegocio_DeveRetornarRespostaEntidadeNaoProcessavel() {
        // Arrange
        BusinessException excecao = new BusinessException("Erro de negócio");

        // Act
        ProblemDetail respostaEntidade = manipuladorExcecoes.businessException(excecao);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), respostaEntidade.getStatus());
    }

    @Test
    void manipularExcecaoViolacaoDeRestricao_DeveRetornarRespostaEntidadeNaoProcessavel() {
        // Arrange
        ConstraintViolationException excecao = mock(ConstraintViolationException.class);
        Set<ConstraintViolation<?>> violacoesRestricao = Set.of();
        when(excecao.getConstraintViolations()).thenReturn(violacoesRestricao);

        // Act
        ProblemDetail respostaEntidade = manipuladorExcecoes.handleConstraintViolationException(excecao);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), respostaEntidade.getStatus());
    }

    @Test
    void manipularExcecaoFeignBadRequest_DeveRetornarRespostaEntidadeNaoProcessavel() throws IOException {
        // Arrange
        FeignException excecao = mock(FeignException.class);
        when(excecao.status()).thenReturn(422);
        when(excecao.responseBody()).thenReturn(Optional.of(ByteBuffer.wrap(new String("{}").getBytes(StandardCharsets.UTF_8))));

        // Act
        ResponseEntity<Object> respostaEntidade = manipuladorExcecoes.handleFeignExceptionBadRequest(excecao);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, respostaEntidade.getStatusCode());
    }

    @Test
    void manipularExcecaoArgumentoNaoValido_DeveRetornarRespostaEntidadeNaoProcessavel() {
        // Arrange
        MethodArgumentNotValidException excecao = mock(MethodArgumentNotValidException.class);
        when(excecao.getAllErrors()).thenReturn(List.of(new FieldError("asp", "asda", "asdas")));

        // Act
        ResponseEntity<Object> respostaEntidade = manipuladorExcecoes.handleMethodArgumentNotValid(excecao, null, null, requisicaoWeb);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, respostaEntidade.getStatusCode());
    }

    @Test
    void manipularExcecaoArgumentoNaoValido_DeveRetornarRespostaEntidadeNaoProcessavelSemErrosDetalhados() {
        // Arrange
        MethodArgumentNotValidException excecao = mock(MethodArgumentNotValidException.class);

        // Act
        ResponseEntity<Object> respostaEntidade = manipuladorExcecoes.handleMethodArgumentNotValid(excecao, null, null, requisicaoWeb);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, respostaEntidade.getStatusCode());
    }
}
