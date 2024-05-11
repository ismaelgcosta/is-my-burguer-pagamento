package br.com.ismyburguer.pagamento.adapter.usecase.impl;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsultaPagamentoUseCaseImplTest {

    @Test
    public void deveConsultarPagamentoComSucesso() {
        // Arrange
        String pagamentoId = "123e4567-e89b-12d3-a456-556642440000";
        Pagamento pagamentoEsperado = new Pagamento(/* informações do pagamento */);
        ConsultarPagamentoRepository repositoryMock = mock(ConsultarPagamentoRepository.class);
        when(repositoryMock.consultar(pagamentoId)).thenReturn(pagamentoEsperado);
        ConsultarPagamentoUseCase useCase = new ConsultaPagamentoUseCaseImpl(repositoryMock);

        // Act
        Pagamento pagamentoRetornado = useCase.consultar(pagamentoId);

        // Assert
        assertEquals(pagamentoEsperado, pagamentoRetornado);
    }
}
