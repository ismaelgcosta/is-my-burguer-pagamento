package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoAPI;
import feign.FeignException;
import org.springframework.validation.annotation.Validated;

@Validated
@WebAdapter
public class AlterarStatusPedidoAPIImpl implements AlterarStatusPedidoAPI {
    private final PedidoAPI pedidoAPI;

    public AlterarStatusPedidoAPIImpl(FeignClientAPI feignClientAPI) {
        this.pedidoAPI = feignClientAPI.createClient(PedidoAPI.class);
    }

    public void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        try {
            pedidoAPI.alterarStatus(pedidoId.getPedidoId(), statusPedido.name());
        } catch (
                FeignException e) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
    }
}
