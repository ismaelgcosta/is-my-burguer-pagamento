package br.com.ismyburguer.controlepedido.adapter.client;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoAPI;
import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@WebAdapter
public class GerarControlePedidoAPIImpl implements GerarControlePedidoAPI {
    private final ControlePedidoAPI controlepedidoAPI;

    public GerarControlePedidoAPIImpl(FeignClientAPI feignClientAPI) {
        this.controlepedidoAPI = feignClientAPI.createClient(ControlePedidoAPI.class);
    }

    @Override
    public UUID gerar(@Valid ControlePedido.PedidoId pedidoId) {
        return controlepedidoAPI.receberPedido(pedidoId.getPedidoId());
    }
}
