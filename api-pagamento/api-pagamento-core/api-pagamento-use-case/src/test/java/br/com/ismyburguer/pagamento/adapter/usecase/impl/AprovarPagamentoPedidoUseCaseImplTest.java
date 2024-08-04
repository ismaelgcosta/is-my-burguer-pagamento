package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
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
public class AprovarPagamentoPedidoUseCaseImplTest {

    @Mock
    private SalvarPagamentoRepository repository;
    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    @Mock
    private GerarControlePedidoUseCase gerarControlePedidoUseCase;

    private AprovarPagamentoPedidoUseCaseImpl aprovarPagamentoPedidoUseCase;

    @BeforeEach
    public void setup() {
        aprovarPagamentoPedidoUseCase = new AprovarPagamentoPedidoUseCaseImpl(repository, alterarStatusPedidoUseCase, gerarControlePedidoUseCase);
    }

    @Test
    public void deveAprovarPagamentoAlterarStatusPedidoParaPagoEGeraControlePedido() {
        Pagamento pagamentoMock = mock(Pagamento.class);
        String pedidoId = UUID.randomUUID().toString();
        when(pagamentoMock.getPedidoId()).thenReturn(new Pagamento.PedidoId(pedidoId));

        aprovarPagamentoPedidoUseCase.aprovarPagamento(pagamentoMock);

        verify(pagamentoMock).validate();
        verify(pagamentoMock).pago();
        verify(repository).salvar(pagamentoMock);
        verify(alterarStatusPedidoUseCase).alterar(new Pedido.PedidoId(pedidoId), Pedido.StatusPedido.PAGO);
        verify(gerarControlePedidoUseCase).receberPedido(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    public void deveAlterarStatusParaAguardandoConfirmacaoEmCasoDeErroEAlancarExcecao() {
        Pagamento pagamentoMock = mock(Pagamento.class);
        String pedidoId = UUID.randomUUID().toString();
        when(pagamentoMock.getPedidoId()).thenReturn(new Pagamento.PedidoId(pedidoId));
        doThrow(new RuntimeException("Erro ao processar pagamento")).when(pagamentoMock).pago();

        assertThrows(Exception.class, () -> aprovarPagamentoPedidoUseCase.aprovarPagamento(pagamentoMock));

        verify(pagamentoMock).validate();
        verify(pagamentoMock).pago();
        verify(repository, never()).salvar(pagamentoMock);
        verify(alterarStatusPedidoUseCase).alterar(new Pedido.PedidoId(pedidoId), Pedido.StatusPedido.AGUARDANDO_CONFIRMACAO_PAGAMENTO);
        verify(gerarControlePedidoUseCase, never()).receberPedido(any());
    }
}
