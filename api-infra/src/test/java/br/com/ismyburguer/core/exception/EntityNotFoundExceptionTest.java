package br.com.ismyburguer.core.exception;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityNotFoundExceptionTest {

    @Test
    void deveRetornarMensagemCorreta() {
        String mensagem = "Erro de negócio ocorreu.";
        EntityNotFoundException exception = new EntityNotFoundException(mensagem);
        assertEquals(mensagem, exception.getMessage());
    }
}