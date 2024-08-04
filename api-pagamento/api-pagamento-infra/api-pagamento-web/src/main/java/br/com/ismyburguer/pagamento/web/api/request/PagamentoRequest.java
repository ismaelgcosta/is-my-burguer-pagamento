package br.com.ismyburguer.pagamento.web.api.request;

import br.com.ismyburguer.pagamento.web.api.enumeration.FormaPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.TipoPagamento;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "pagamentoId")
public class PagamentoRequest {

    private UUID pagamentoId = UUID.randomUUID();

    private UUID pedidoId;

    private StatusPagamento statusPagamento;
    private TipoPagamento tipoPagamento;

    private FormaPagamento formaPagamento;

    private BigDecimal valorTotal;

    private String qrCode;

    public PagamentoRequest(UUID pedidoId, StatusPagamento statusPagamento, TipoPagamento tipoPagamento, FormaPagamento formaPagamento, BigDecimal valorTotal, String qrCode) {
        this.pedidoId = pedidoId;
        this.statusPagamento = statusPagamento;
        this.tipoPagamento = tipoPagamento;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.qrCode = qrCode;
    }
}
