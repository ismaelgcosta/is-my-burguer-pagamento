package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.pagamento.adapter.model.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.adapter.model.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.model.TipoPagamento;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;

@PersistenceConverter
public class PagamentoToPagamentoModelConverter implements Converter<Pagamento, PagamentoModel> {
    @Override
    public PagamentoModel convert(Pagamento source) {
        return new PagamentoModel(
                source.getPedidoId().getPedidoId(),
                StatusPagamento.valueOf(source.getStatusPagamento().name()),
                TipoPagamento.valueOf(source.getTipoPagamento().name()),
                FormaPagamento.valueOf(source.getFormaPagamento().name()),
                source.getTotal().getValor(),
                source.getQrCode()
        );
    }
}
