package br.com.ismyburguer.pagamento.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.FormaPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;

@WebConverter
public class PagamentoToPagamentoRequestConverter implements Converter<Pagamento, PagamentoRequest> {
    @Override
    public PagamentoRequest convert(Pagamento source) {
        return new PagamentoRequest(
                source.getPedidoId().getPedidoId(),
                StatusPagamento.valueOf(source.getStatusPagamento().name()),
                TipoPagamento.valueOf(source.getTipoPagamento().name()),
                FormaPagamento.valueOf(source.getFormaPagamento().name()),
                source.getTotal().getValor(),
                source.getQrCode()
        );
    }
}
