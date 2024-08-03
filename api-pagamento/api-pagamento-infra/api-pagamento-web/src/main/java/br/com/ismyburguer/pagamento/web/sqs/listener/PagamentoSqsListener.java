package br.com.ismyburguer.pagamento.web.sqs.listener;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.EstornarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.GerarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.converter.PagamentoRequestToPagamentoConverter;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = {"dev", "production"})
public class PagamentoSqsListener {

    private final GerarPagamentoPedidoUseCase gerarPagamentoPedidoUseCase;
    private final EstornarPagamentoPedidoUseCase estornarPagamentoPedidoUseCase;
    private final PagamentoRequestToPagamentoConverter pagamentoRequestToPagamentoConverter;
    private final ObjectMapper objectMapper;

    public PagamentoSqsListener(GerarPagamentoPedidoUseCase gerarPagamentoPedidoUseCase,
                                EstornarPagamentoPedidoUseCase estornarPagamentoPedidoUseCase,
                                PagamentoRequestToPagamentoConverter pagamentoRequestToPagamentoConverter,
                                ObjectMapper objectMapper) {
        this.gerarPagamentoPedidoUseCase = gerarPagamentoPedidoUseCase;
        this.estornarPagamentoPedidoUseCase = estornarPagamentoPedidoUseCase;
        this.pagamentoRequestToPagamentoConverter = pagamentoRequestToPagamentoConverter;
        this.objectMapper = objectMapper;
    }

    @SqsListener(value = "${events.queues.is-my-burguer-pagamento-queue}")
    public void gerarPagamento(String message) {
        try {
            PagamentoRequest pagamentoRequest = objectMapper.readValue(message, PagamentoRequest.class);
            if(pagamentoRequest.getStatusPagamento() == StatusPagamento.ESTORNADO) {
                estornarPagamentoPedidoUseCase.estornarPagamento(new Pagamento.PedidoId(pagamentoRequest.getPedidoId()));
            } else {
                Pagamento pagamento = pagamentoRequestToPagamentoConverter.convert(pagamentoRequest);
                gerarPagamentoPedidoUseCase.iniciarPagamento(pagamento);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao realizar pagamento do pedido", e);
        }
    }
}
