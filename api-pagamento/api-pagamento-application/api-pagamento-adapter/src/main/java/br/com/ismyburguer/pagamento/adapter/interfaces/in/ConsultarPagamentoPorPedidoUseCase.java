package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.hibernate.validator.constraints.UUID;

public interface ConsultarPagamentoPorPedidoUseCase {

    Pagamento consultar(@UUID String pedidoId);
}
