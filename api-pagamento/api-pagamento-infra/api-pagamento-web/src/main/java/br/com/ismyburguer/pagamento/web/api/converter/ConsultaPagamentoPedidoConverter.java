package br.com.ismyburguer.pagamento.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.response.ConsultaPagamentoPedidoResponse;
import br.com.ismyburguer.pagamento.web.api.response.FormaPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.StatusPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.TipoPagamentoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class ConsultaPagamentoPedidoConverter implements Converter<Pagamento, ConsultaPagamentoPedidoResponse> {

    @Override
    public ConsultaPagamentoPedidoResponse convert(Pagamento source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new ConsultaPagamentoPedidoResponse(
                source.getPagamentoId().getPagamentoId(),
                new StatusPagamentoResponse(source.getStatusPagamento().name(), source.getStatusPagamento().getDescricao()),
                new FormaPagamentoResponse(source.getFormaPagamento().name(), source.getFormaPagamento().getDescricao()),
                new TipoPagamentoResponse(source.getTipoPagamento().name(), source.getTipoPagamento().getDescricao()),
                source.getQrCode()
        );
    }
}
