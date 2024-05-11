package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagamentoToPagamentoModelConverterTest {

    @Test
    void deveConverterPagamentoParaPagamentoModel() {
        // Arrange
        Pagamento pagamento = new Pagamento(
                new Pagamento.PedidoId(UUID.randomUUID()),
                new Pagamento.Total(BigDecimal.valueOf(100.0)),
                Pagamento.StatusPagamento.PAGO,
                Pagamento.FormaPagamento.MERCADO_PAGO,
                Pagamento.TipoPagamento.QR_CODE,
                "QR_CODE_TESTE"
        );

        PagamentoToPagamentoModelConverter converter = new PagamentoToPagamentoModelConverter();

        // Act
        PagamentoModel pagamentoModel = converter.convert(pagamento);

        // Assert
        assertNotNull(pagamentoModel);
        assertEquals(pagamento.getPedidoId().getPedidoId(), pagamentoModel.getPedidoId());
        assertEquals(pagamento.getStatusPagamento().name(), pagamentoModel.getStatusPagamento().name());
        assertEquals(pagamento.getTipoPagamento().name(), pagamentoModel.getTipoPagamento().name());
        assertEquals(pagamento.getFormaPagamento().name(), pagamentoModel.getFormaPagamento().name());
        assertEquals(pagamento.getTotal().getValor(), pagamentoModel.getValorTotal());
        assertEquals(pagamento.getQrCode(), pagamentoModel.getQrCode());
    }
}
