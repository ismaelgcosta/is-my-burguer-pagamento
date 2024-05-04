package br.com.ismyburguer.pagamento.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;

@WebConverter
public class PagamentoRequestToPagamentoConverter implements Converter<PagamentoRequest, Pagamento> {
    @Override
    public Pagamento convert(PagamentoRequest source) {
        return new Pagamento(
                new Pagamento.PedidoId(source.getPedidoId()),
                new Pagamento.Total(source.getValorTotal()),
                Pagamento.StatusPagamento.valueOf(source.getStatusPagamento().name()),
                Pagamento.FormaPagamento.valueOf(source.getFormaPagamento().name()),
                Pagamento.TipoPagamento.valueOf(source.getTipoPagamento().name()),
                source.getQrCode()
        );
    }
}
