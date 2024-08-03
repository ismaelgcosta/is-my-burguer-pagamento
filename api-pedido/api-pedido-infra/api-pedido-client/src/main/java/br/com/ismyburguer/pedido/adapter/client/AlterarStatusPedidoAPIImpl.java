package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pedido.adapter.request.PedidoRequest;
import br.com.ismyburguer.pedido.adapter.request.StatusPedidoRequest;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsSendOptions;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.util.function.Consumer;

@Validated
@WebAdapter
public class AlterarStatusPedidoAPIImpl implements AlterarStatusPedidoAPI {
    private final SqsTemplate sqsTemplate;

    @Setter
    @Value("${events.queues.is-my-burguer-pedido-queue}")
    private String pedidoQueue;

    private final ObjectMapper objectMapper;

    public AlterarStatusPedidoAPIImpl(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = to -> {
            try {
                to.queue(pedidoQueue).payload(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(new PedidoRequest(
                        pedidoId.getPedidoId(),
                        StatusPedidoRequest.valueOf(statusPedido.name())
                )));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao alterar status do pedido", e);
            }
        };
        sqsTemplate.send(sqsSendOptionsConsumer);
    }
}
