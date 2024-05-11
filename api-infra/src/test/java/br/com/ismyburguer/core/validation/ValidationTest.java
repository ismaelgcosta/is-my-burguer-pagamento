package br.com.ismyburguer.core.validation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
class ValidationTest {
    private Validation dummy;

    // Criando uma classe anônima simulada que implementa a interface Validation

    @Setter
    public static final class Dummy implements br.com.ismyburguer.core.validation.Validation {
        @NotNull(message = "não deve ser nulo")
        private String notNull;
    };

    @BeforeEach
    void setUp() {
        dummy = new Validation(){
            @NotNull(message = "não deve ser nulo")
            String notNull;

        };
    }

    @Test
    void deveValidarSemViolacoes() {
        ReflectionTestUtils.setField(dummy, "notNull", "a");
        assertDoesNotThrow(() -> dummy.validate());
    }

    @Test
    void deveLancarExcecaoParaViolacoes() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> dummy.validate());
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("notNull", exception.getConstraintViolations().iterator().next().getPropertyPath().toString());
    }
}
