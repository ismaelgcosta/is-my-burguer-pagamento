package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GerarPagamentoPedidoUseCaseImplTest {

    @Mock
    private EfetuarPagamentoUseCase pagamentoUseCase;

    @Mock
    private ConsultarPagamentoUseCase consultarPagamentoUseCase;

    @Mock
    private ConsultarPedidoUseCase pedidoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @Mock
    private SalvarPagamentoRepository pagamentoRepository;

    @InjectMocks
    private GerarPagamentoPedidoUseCaseImpl pagarPedidoUseCase;

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

        // Act
        pagarPedidoUseCase.iniciarPagamento(new Pagamento(new Pagamento.PedidoId(pedidoId), new Pagamento.Total(BigDecimal.valueOf(100.00d))));

        // Assert
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(pagamentoRepository, times(1)).salvar(any());
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

        // Act
        pagarPedidoUseCase.iniciarPagamento(new Pagamento(new Pagamento.PedidoId(pedidoId), new Pagamento.Total(BigDecimal.valueOf(100.00d))));

        // Assert
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(pagamentoRepository, times(1)).salvar(any());
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

        // Act
        pagarPedidoUseCase.iniciarPagamento(new Pagamento(new Pagamento.PedidoId(pedidoId), new Pagamento.Total(BigDecimal.valueOf(100.00d))));

        // Assert
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(pagamentoRepository, times(1)).salvar(any());
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

        // Act
        pagarPedidoUseCase.iniciarPagamento(new Pagamento(new Pagamento.PedidoId(pedidoId), new Pagamento.Total(BigDecimal.valueOf(100.00d))));

        // Assert
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        verify(pagamentoRepository, times(1)).salvar(any());
    }


}
