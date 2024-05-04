package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoAPI;

@UseCase
public class AlterarStatusPedidoUseCaseImpl implements AlterarStatusPedidoUseCase {
    private final AlterarStatusPedidoAPI statusPedidoAPI;
    public AlterarStatusPedidoUseCaseImpl(AlterarStatusPedidoAPI statusPedidoAPI) {
        this.statusPedidoAPI = statusPedidoAPI;
    }

    @Override
    public void alterar(Pagamento.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        statusPedidoAPI.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), statusPedido);
    }
}
