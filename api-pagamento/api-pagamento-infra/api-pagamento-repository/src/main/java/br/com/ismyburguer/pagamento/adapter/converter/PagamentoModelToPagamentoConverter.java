package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;

@PersistenceConverter
public class PagamentoModelToPagamentoConverter implements Converter<PagamentoModel, Pagamento> {
    @Override
    public Pagamento convert(PagamentoModel source) {
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
