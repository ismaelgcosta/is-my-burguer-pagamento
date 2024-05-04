package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;

public interface AlterarStatusPedidoAPI {
    void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido);
}
