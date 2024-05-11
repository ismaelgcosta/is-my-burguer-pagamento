package br.com.ismyburguer.core.validation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ValidationTest {
    private Dummy dummy;

    // Criando uma classe anônima simulada que implementa a interface Validation

    @Setter
    public static final class Dummy implements br.com.ismyburguer.core.validation.Validation {
        @NotNull(message = "não deve ser nulo")
        private String notNull;
    };

    @BeforeEach
    void setUp() {
        dummy = new Dummy();
    }

    @Test
    void deveValidarSemViolacoes() {
        dummy.setNotNull("a");
        assertDoesNotThrow(() -> dummy.validate());
    }

    @Test
    void deveLancarExcecaoParaViolacoes() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> dummy.validate());
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("notNull", exception.getConstraintViolations().iterator().next().getPropertyPath().toString());
    }
}
