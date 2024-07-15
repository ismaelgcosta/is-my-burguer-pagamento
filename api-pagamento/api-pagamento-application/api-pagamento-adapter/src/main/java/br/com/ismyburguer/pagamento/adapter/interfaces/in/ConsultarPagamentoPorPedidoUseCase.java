package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public interface ConsultarPagamentoPorPedidoUseCase {

    Pagamento consultar(@UUID String pedidoId);
    default Pagamento consultar(@NotNull @UUID java.util.UUID pedidoId) {
        return consultar(pedidoId.toString());
    }
}
