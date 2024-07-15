package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;

import java.util.UUID;

public interface GerarPagamentoPedidoUseCase {

    UUID iniciarPagamento(Pagamento pagamento);

}
