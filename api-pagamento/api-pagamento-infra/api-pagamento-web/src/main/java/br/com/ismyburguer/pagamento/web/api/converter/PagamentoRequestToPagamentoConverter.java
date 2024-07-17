package br.com.ismyburguer.pagamento.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;

import java.util.Optional;

@WebConverter
public class PagamentoRequestToPagamentoConverter implements Converter<PagamentoRequest, Pagamento> {
    @Override
    public Pagamento convert(PagamentoRequest source) {
        return new Pagamento(
                new Pagamento.PagamentoId(source.getPagamentoId()),
                new Pagamento.PedidoId(source.getPedidoId()),
                new Pagamento.Total(source.getValorTotal()),
                Optional.ofNullable(source.getStatusPagamento()).map(Enum::name).map(Pagamento.StatusPagamento::valueOf).orElse(null),
                Optional.ofNullable(source.getFormaPagamento()).map(Enum::name).map(Pagamento.FormaPagamento::valueOf).orElse(null),
                Optional.ofNullable(source.getTipoPagamento()).map(Enum::name).map(Pagamento.TipoPagamento::valueOf).orElse(null),
                source.getQrCode()
        );
    }
}
