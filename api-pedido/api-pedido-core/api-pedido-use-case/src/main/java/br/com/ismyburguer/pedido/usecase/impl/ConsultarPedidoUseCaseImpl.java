package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoAPI;

@UseCase
public class ConsultarPedidoUseCaseImpl implements ConsultarPedidoUseCase {

    private final ConsultarPedidoAPI consultarPedidoAPI;

    public ConsultarPedidoUseCaseImpl(ConsultarPedidoAPI consultarPedidoAPI) {
        this.consultarPedidoAPI = consultarPedidoAPI;
    }

    @Override
    public Pedido buscarPorId(Pedido.PedidoId pedidoId) {
        return consultarPedidoAPI.obterPeloPedidoId(pedidoId.getPedidoId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não foi encontrado"));
    }
}
