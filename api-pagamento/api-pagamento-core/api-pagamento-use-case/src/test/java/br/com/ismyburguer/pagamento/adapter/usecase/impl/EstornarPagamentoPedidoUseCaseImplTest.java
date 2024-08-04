package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoPorPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EstornarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstornarPagamentoPedidoUseCaseImplTest {

    @Mock
    private SalvarPagamentoRepository repository;
    @Mock
    private ConsultarPagamentoPorPedidoUseCase consultarPagamentoPorPedidoUseCase;

    private EstornarPagamentoPedidoUseCaseImpl estornarPagamentoPedidoUseCase;

    @BeforeEach
    void setUp() {
        estornarPagamentoPedidoUseCase = new EstornarPagamentoPedidoUseCaseImpl(repository, consultarPagamentoPorPedidoUseCase);
    }

    @Test
    void deveEstornarPagamentoValido() {
        Pagamento pagamentoMock = mock(Pagamento.class);
        when(consultarPagamentoPorPedidoUseCase.consultar(any(UUID.class))).thenReturn(pagamentoMock);

        UUID pedidoId = UUID.randomUUID();
        estornarPagamentoPedidoUseCase.estornarPagamento(new Pagamento.PedidoId(pedidoId));

        verify(consultarPagamentoPorPedidoUseCase).consultar(pedidoId);
        verify(pagamentoMock).validate();
        verify(pagamentoMock).estornado();
        verify(repository).salvar(pagamentoMock);
    }

    @Test
    void devePropagarExcecaoNaConsulta() {
        when(consultarPagamentoPorPedidoUseCase.consultar(any(UUID.class))).thenThrow(new RuntimeException("Erro ao consultar pagamento"));

        UUID pedidoId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> estornarPagamentoPedidoUseCase.estornarPagamento(new Pagamento.PedidoId(pedidoId)));

        verify(consultarPagamentoPorPedidoUseCase).consultar(pedidoId);
        verify(repository, never()).salvar(any());
    }

}