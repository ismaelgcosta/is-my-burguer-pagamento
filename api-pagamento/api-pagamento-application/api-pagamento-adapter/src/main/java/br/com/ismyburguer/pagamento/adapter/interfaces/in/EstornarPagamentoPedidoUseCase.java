package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import jakarta.validation.constraints.NotNull;

public interface EstornarPagamentoPedidoUseCase {
    void estornarPagamento(Pagamento.@NotNull PedidoId pedidoId);

}
