package br.com.ismyburguer.pagamento.web.api.converter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.FormaPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagamentoRequestToPagamentoConverterTest {

    private PagamentoRequestToPagamentoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PagamentoRequestToPagamentoConverter();
    }

    @Test
    void testConvert() {
        // Configuração
        PagamentoRequest request = new PagamentoRequest();
        UUID pedidoId = UUID.randomUUID();
        request.setPedidoId(pedidoId);
        request.setValorTotal(BigDecimal.valueOf(100.0));
        request.setStatusPagamento(StatusPagamento.PAGO);
        request.setFormaPagamento(FormaPagamento.MERCADO_PAGO);
        request.setTipoPagamento(TipoPagamento.QR_CODE);
        request.setQrCode("qr-code");

        // Execução
        Pagamento pagamento = converter.convert(request);

        // Verificação
        assertNotNull(pagamento);
        assertEquals(pedidoId, pagamento.getPedidoId().getPedidoId());
        assertEquals(BigDecimal.valueOf(100.0), pagamento.getTotal().getValor());
        assertEquals(Pagamento.StatusPagamento.PAGO, pagamento.getStatusPagamento());
        assertEquals(Pagamento.FormaPagamento.MERCADO_PAGO, pagamento.getFormaPagamento());
        assertEquals(Pagamento.TipoPagamento.QR_CODE, pagamento.getTipoPagamento());
        assertEquals("qr-code", pagamento.getQrCode());
    }

}
