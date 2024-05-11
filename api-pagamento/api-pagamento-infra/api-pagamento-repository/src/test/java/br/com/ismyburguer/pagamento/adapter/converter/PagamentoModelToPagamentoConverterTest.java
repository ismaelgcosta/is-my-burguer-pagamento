package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.pagamento.adapter.model.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.adapter.model.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.model.TipoPagamento;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagamentoModelToPagamentoConverterTest {

    @Test
    public void deveConverterPagamentoModelParaPagamentoComSucesso() {
        // Arrange
        PagamentoModelToPagamentoConverter converter = new PagamentoModelToPagamentoConverter();
        PagamentoModel pagamentoModel = new PagamentoModel();
        UUID pedidoId = UUID.randomUUID();
        pagamentoModel.setPedidoId(pedidoId);
        pagamentoModel.setValorTotal(BigDecimal.valueOf(100.0));
        pagamentoModel.setStatusPagamento(StatusPagamento.PAGO);
        pagamentoModel.setFormaPagamento(FormaPagamento.MERCADO_PAGO);
        pagamentoModel.setTipoPagamento(TipoPagamento.QR_CODE);
        pagamentoModel.setQrCode("qrCode");

        // Act
        Pagamento pagamento = converter.convert(pagamentoModel);

        // Assert
        assertNotNull(pagamento);
        assertEquals(pedidoId, pagamento.getPedidoId().getPedidoId());
        assertEquals(BigDecimal.valueOf(100.0), pagamento.getTotal().getValor());
        assertEquals(Pagamento.StatusPagamento.PAGO, pagamento.getStatusPagamento());
        assertEquals(Pagamento.FormaPagamento.MERCADO_PAGO, pagamento.getFormaPagamento());
        assertEquals(Pagamento.TipoPagamento.QR_CODE, pagamento.getTipoPagamento());
        assertEquals("qrCode", pagamento.getQrCode());
    }

}
