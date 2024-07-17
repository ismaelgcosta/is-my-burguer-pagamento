package br.com.ismyburguer.pagamento.web.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ConsultaPagamentoPedidoResponse {

    private UUID pagamentoId;
    private StatusPagamentoResponse statusPagamento;
    private FormaPagamentoResponse formaPagamento;
    private TipoPagamentoResponse tipoPagamento;

    @Setter
    private String qrCode;
}
