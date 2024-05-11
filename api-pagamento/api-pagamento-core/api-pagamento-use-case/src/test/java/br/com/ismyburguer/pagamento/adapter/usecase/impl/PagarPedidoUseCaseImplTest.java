package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagarPedidoUseCaseImplTest {

    @Mock
    private EfetuarPagamentoUseCase pagamentoUseCase;

    @Mock
    private ConsultarPagamentoUseCase consultarPagamentoUseCase;

    @Mock
    private ConsultarPedidoUseCase pedidoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @Mock
    private GerarControlePedidoUseCase gerarControlePedidoUseCase;

    @InjectMocks
    private PagarPedidoUseCaseImpl pagarPedidoUseCase;

    @Test
    void devePagarPedidoComSucesso() {
        // Arrange
        UUID pagamentoId = UUID.randomUUID();
        String qrCode = "CODIGO_QR";
        String pedidoId = UUID.randomUUID().toString();

        Pagamento pagamento = new Pagamento();
        pagamento.setQrCode(qrCode);
        pagamento.pago();

        Pedido pedido = new Pedido(new Pedido.PedidoId(pedidoId), null, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO, null);
        pedido.setTotal(BigDecimal.valueOf(100.0));

        when(pedidoUseCase.buscarPorId(any())).thenReturn(pedido);
        when(pagamentoUseCase.pagar(any())).thenReturn(pagamentoId);
        when(consultarPagamentoUseCase.consultar(any())).thenReturn(pagamento);

        // Act
        String result = pagarPedidoUseCase.pagar(new Pagamento.PedidoId(pedidoId));

        // Assert
        assertEquals(qrCode, result);
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.PAGO));
        verify(gerarControlePedidoUseCase, times(1)).receberPedido(any());
    }

    @Test
    void devePagarPedidoFechadoComSucesso() {
        // Arrange
        UUID pagamentoId = UUID.randomUUID();
        String qrCode = "CODIGO_QR";
        String pedidoId = UUID.randomUUID().toString();

        Pagamento pagamento = new Pagamento();
        pagamento.setQrCode(qrCode);
        pagamento.pago();

        Pedido.PedidoId pedidoIdPedido = new Pedido.PedidoId(pedidoId);
        Pedido pedido = new Pedido(pedidoIdPedido, null, Pedido.StatusPedido.FECHADO, null);
        pedido.setTotal(BigDecimal.valueOf(100.0));

        when(pedidoUseCase.buscarPorId(any())).thenReturn(pedido);
        when(pagamentoUseCase.pagar(any())).thenReturn(pagamentoId);
        when(consultarPagamentoUseCase.consultar(any())).thenReturn(pagamento);

        // Act
        String result = pagarPedidoUseCase.pagar(new Pagamento.PedidoId(pedidoId));

        // Assert
        assertEquals(qrCode, result);
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.PAGO));
        verify(gerarControlePedidoUseCase, times(1)).receberPedido(any());
    }

    @Test
    void devePagarPedidoNaoAutorizadoComSucesso() {
        // Arrange
        UUID pagamentoId = UUID.randomUUID();
        String qrCode = "CODIGO_QR";
        String pedidoId = UUID.randomUUID().toString();

        Pagamento pagamento = new Pagamento();
        pagamento.setQrCode(qrCode);
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.NAO_AUTORIZADO);

        Pedido.PedidoId pedidoIdPedido = new Pedido.PedidoId(pedidoId);
        Pedido pedido = new Pedido(pedidoIdPedido, null, Pedido.StatusPedido.FECHADO, null);
        pedido.setTotal(BigDecimal.valueOf(100.0));

        when(pedidoUseCase.buscarPorId(any())).thenReturn(pedido);
        when(pagamentoUseCase.pagar(any())).thenReturn(pagamentoId);
        when(consultarPagamentoUseCase.consultar(any())).thenReturn(pagamento);

        // Act
        String result = pagarPedidoUseCase.pagar(new Pagamento.PedidoId(pedidoId));

        // Assert
        assertEquals(qrCode, result);
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(gerarControlePedidoUseCase, times(0)).receberPedido(any());
        verify(alterarStatusPedidoUseCase).alterar(any(), eq(Pedido.StatusPedido.PAGAMENTO_NAO_AUTORIZADO));
    }


    @Test
    void devePagarPedidoAguardandoAutorizacaoComSucesso() {
        // Arrange
        UUID pagamentoId = UUID.randomUUID();
        String qrCode = "CODIGO_QR";
        String pedidoId = UUID.randomUUID().toString();

        Pagamento pagamento = new Pagamento();
        pagamento.setQrCode(qrCode);
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.AGUARDANDO_CONFIRMACAO);

        Pedido.PedidoId pedidoIdPedido = new Pedido.PedidoId(pedidoId);
        Pedido pedido = new Pedido(pedidoIdPedido, null, Pedido.StatusPedido.FECHADO, null);
        pedido.setTotal(BigDecimal.valueOf(100.0));

        when(pedidoUseCase.buscarPorId(any())).thenReturn(pedido);
        when(pagamentoUseCase.pagar(any())).thenReturn(pagamentoId);
        when(consultarPagamentoUseCase.consultar(any())).thenReturn(pagamento);

        // Act
        String result = pagarPedidoUseCase.pagar(new Pagamento.PedidoId(pedidoId));

        // Assert
        assertEquals(qrCode, result);
        verify(alterarStatusPedidoUseCase, times(2)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(gerarControlePedidoUseCase, times(0)).receberPedido(any());
    }


}
