package br.com.ismyburguer.pagamento.gateway.out;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.hibernate.validator.constraints.UUID;

public interface ConsultarPagamentoPorPedidoRepository {

    Pagamento consultar(@UUID String pedidoId);

}
