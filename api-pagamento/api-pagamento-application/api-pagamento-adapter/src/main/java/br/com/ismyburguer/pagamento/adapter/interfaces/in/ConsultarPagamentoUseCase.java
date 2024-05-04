package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.hibernate.validator.constraints.UUID;

public interface ConsultarPagamentoUseCase {

    Pagamento consultar(@UUID String pagamentoId);
}
