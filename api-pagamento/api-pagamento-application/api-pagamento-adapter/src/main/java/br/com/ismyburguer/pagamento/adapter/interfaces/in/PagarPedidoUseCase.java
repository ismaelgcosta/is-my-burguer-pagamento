package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;

public interface PagarPedidoUseCase {
    String pagar(Pagamento.PedidoId pedidoId);

}
