package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoAPI;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class GerarControlePedidoUseCaseImpl implements GerarControlePedidoUseCase {

    private final GerarControlePedidoAPI gerarControlePedidoAPI;

    public GerarControlePedidoUseCaseImpl(GerarControlePedidoAPI gerarControlePedidoAPI) {
        this.gerarControlePedidoAPI = gerarControlePedidoAPI;
    }

    @Override
    public void receberPedido(@Valid ControlePedido.PedidoId pedidoId) {
        gerarControlePedidoAPI.gerar(pedidoId);
    }

}
