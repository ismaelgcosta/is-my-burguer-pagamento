package br.com.ismyburguer.core.exception;

import br.com.ismyburguer.core.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessExceptionTest {

    @Test
    void deveRetornarMensagemCorreta() {
        String mensagem = "Erro de negócio ocorreu.";
        BusinessException exception = new BusinessException(mensagem);
        assertEquals(mensagem, exception.getMessage());
    }
}
