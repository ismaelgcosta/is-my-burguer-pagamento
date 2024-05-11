package br.com.ismyburguer.pagamento.web.api.converter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.response.ConsultaPagamentoPedidoResponse;
import br.com.ismyburguer.pagamento.web.api.response.FormaPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.StatusPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.TipoPagamentoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsultaPagamentoPedidoConverterTest {

    private ConsultaPagamentoPedidoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ConsultaPagamentoPedidoConverter();
    }

    @Test
    void testConvert_Success() {
        // Configuração
        Pagamento pagamento = new Pagamento(
                new Pagamento.PedidoId(UUID.randomUUID()),
                new Pagamento.Total(BigDecimal.ONE),
                Pagamento.StatusPagamento.PAGO,
                Pagamento.FormaPagamento.MERCADO_PAGO,
                Pagamento.TipoPagamento.QR_CODE,
                null
        );
        pagamento.setQrCode("qr-code");

        // Execução
        ConsultaPagamentoPedidoResponse response = converter.convert(pagamento);

        // Verificação
        assertNotNull(response);
        assertEquals("PAGO", response.getStatusPagamento().getValor());
        assertEquals("Pago", response.getStatusPagamento().getDescricao());
        assertEquals("MERCADO_PAGO", response.getFormaPagamento().getValor());
        assertEquals("Mercado Pago", response.getFormaPagamento().getDescricao());
        assertEquals("QR_CODE", response.getTipoPagamento().getValor());
        assertEquals("QR Code", response.getTipoPagamento().getDescricao());
        assertEquals("qr-code", response.getQrCode());
    }

}
