package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.AprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AtualizarStatusPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ReprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPagamentoPedidoUseCaseImplTest {

    @Mock
    private AprovarPagamentoPedidoUseCase aprovarPagamentoPedidoUseCase;
    @Mock
    private ReprovarPagamentoPedidoUseCase reprovarPagamentoPedidoUseCase;

    private AtualizarStatusPagamentoPedidoUseCase atualizarStatusPagamentoPedidoUseCase;

    @BeforeEach
    void setUp() {
        atualizarStatusPagamentoPedidoUseCase = new AtualizarStatusPagamentoPedidoUseCaseImpl(aprovarPagamentoPedidoUseCase, reprovarPagamentoPedidoUseCase);
    }

    @Test
    void deveAprovarPagamentoQuandoStatusForPago() {
        Pagamento pagamento = new Pagamento();
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.PAGO);

        atualizarStatusPagamentoPedidoUseCase.atualizarStatus(pagamento, Pagamento.StatusPagamento.PAGO);

        verify(aprovarPagamentoPedidoUseCase).aprovarPagamento(pagamento);
        verify(reprovarPagamentoPedidoUseCase, never()).reprovarPagamento(any());
    }

    @Test
    void deveReprovarPagamentoQuandoStatusForNaoAutorizado() {
        Pagamento pagamento = new Pagamento();
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.NAO_AUTORIZADO);

        atualizarStatusPagamentoPedidoUseCase.atualizarStatus(pagamento, Pagamento.StatusPagamento.NAO_AUTORIZADO);

        verify(aprovarPagamentoPedidoUseCase, never()).aprovarPagamento(any());
        verify(reprovarPagamentoPedidoUseCase).reprovarPagamento(pagamento);
    }

    @Test
    void naoDeveChamarNenhumMetodoQuandoStatusForInvalido() {
        Pagamento pagamento = new Pagamento();
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.AGUARDANDO_CONFIRMACAO); // Status inválido para este caso

        atualizarStatusPagamentoPedidoUseCase.atualizarStatus(pagamento, Pagamento.StatusPagamento.AGUARDANDO_CONFIRMACAO);

        verify(aprovarPagamentoPedidoUseCase, never()).aprovarPagamento(any());
        verify(reprovarPagamentoPedidoUseCase, never()).reprovarPagamento(any());
    }
}
