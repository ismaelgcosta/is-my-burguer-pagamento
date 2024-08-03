package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EfetuarPagamentoUseCaseImplTest {

    private SalvarPagamentoRepository repositoryMock;
    private EfetuarPagamentoUseCase useCase;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(SalvarPagamentoRepository.class);
        useCase = new EfetuarPagamentoUseCaseImpl(repositoryMock);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void deveEfetuarPagamentoComSucesso() {
        // Arrange
        Pagamento pagamento = spy(EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class));
        UUID pagamentoIdEsperado = UUID.randomUUID();
        when(repositoryMock.salvar(pagamento)).thenReturn(pagamentoIdEsperado);

        // Act
        UUID pagamentoIdRetornado = useCase.pagar(pagamento);

        // Assert
        assertNotNull(pagamentoIdRetornado);
        assertEquals(pagamentoIdEsperado, pagamentoIdRetornado);
        assertSame(pagamento.getStatusPagamento(), Pagamento.StatusPagamento.AGUARDANDO_CONFIRMACAO);
        assertNotNull(pagamento.getQrCode());

        // Verifica se o método de validação foi chamado
        verify(pagamento).validate();
    }

}
