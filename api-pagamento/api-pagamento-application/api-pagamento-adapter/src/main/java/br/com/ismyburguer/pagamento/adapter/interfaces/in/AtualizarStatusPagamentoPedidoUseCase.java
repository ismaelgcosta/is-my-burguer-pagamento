package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;

public interface AtualizarStatusPagamentoPedidoUseCase {
    void atualizarStatus(Pagamento pagamento, Pagamento.StatusPagamento statusPagamento);

}
