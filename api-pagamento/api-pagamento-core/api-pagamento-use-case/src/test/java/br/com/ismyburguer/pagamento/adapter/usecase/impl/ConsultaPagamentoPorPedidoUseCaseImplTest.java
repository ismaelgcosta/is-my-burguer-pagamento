package br.com.ismyburguer.pagamento.adapter.usecase.impl;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoPorPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoPorPedidoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsultaPagamentoPorPedidoUseCaseImplTest {

    @Test
    public void deveConsultarPagamentoPorPedidoComSucesso() {
        // Arrange
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";
        Pagamento pagamentoEsperado = new Pagamento(/* informações do pagamento */);
        ConsultarPagamentoPorPedidoRepository repositoryMock = mock(ConsultarPagamentoPorPedidoRepository.class);
        when(repositoryMock.consultar(pedidoId)).thenReturn(pagamentoEsperado);
        ConsultarPagamentoPorPedidoUseCase useCase = new ConsultaPagamentoPorPedidoUseCaseImpl(repositoryMock);

        // Act
        Pagamento pagamentoRetornado = useCase.consultar(pedidoId);

        // Assert
        assertEquals(pagamentoEsperado, pagamentoRetornado);
    }
}
