package br.com.ismyburguer.pedido.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.entity.Pedido;

public interface AlterarStatusPedidoUseCase {

    void alterar(Pagamento.PedidoId pedidoId, Pedido.StatusPedido statusPedido);
}
