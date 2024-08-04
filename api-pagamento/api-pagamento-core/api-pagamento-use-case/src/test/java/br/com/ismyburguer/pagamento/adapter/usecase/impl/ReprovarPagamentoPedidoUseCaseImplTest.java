package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.ReprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.ConstraintViolationException;
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
public class ReprovarPagamentoPedidoUseCaseImplTest {

    @Mock
    private SalvarPagamentoRepository repository;
    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    private ReprovarPagamentoPedidoUseCase reprovarPagamentoPedidoUseCase;

    @BeforeEach
    public void setUp() {
        reprovarPagamentoPedidoUseCase = new ReprovarPagamentoPedidoUseCaseImpl(repository, alterarStatusPedidoUseCase);
    }

    @Test
    public void deveReprovarPagamentoValido() {
        Pagamento pagamentoMock = mock(Pagamento.class);
        when(pagamentoMock.getPedidoId()).thenReturn(new Pagamento.PedidoId(UUID.randomUUID()));

        reprovarPagamentoPedidoUseCase.reprovarPagamento(pagamentoMock);

        verify(pagamentoMock).validate();
        verify(pagamentoMock).naoAutorizado();
        verify(repository).salvar(pagamentoMock);
        verify(alterarStatusPedidoUseCase).alterar(new Pedido.PedidoId(pagamentoMock.getPedidoId().getPedidoId()), Pedido.StatusPedido.PAGAMENTO_NAO_AUTORIZADO);
    }

    @Test
    public void devePropagarExcecaoNaValidacao() {
        Pagamento pagamentoMock = mock(Pagamento.class);
        doThrow(new ConstraintViolationException("Erro de validação", null)).when(pagamentoMock).validate();

        assertThrows(ConstraintViolationException.class, () -> reprovarPagamentoPedidoUseCase.reprovarPagamento(pagamentoMock));

        verify(pagamentoMock).validate();
        verify(pagamentoMock, never()).naoAutorizado();
        verify(repository, never()).salvar(any());
        verify(alterarStatusPedidoUseCase, never()).alterar(any(), any());
    }

}
