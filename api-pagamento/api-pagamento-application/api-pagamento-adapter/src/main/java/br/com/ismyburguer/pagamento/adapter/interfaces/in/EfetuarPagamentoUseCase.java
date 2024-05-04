package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import jakarta.validation.Valid;

import java.util.UUID;

public interface EfetuarPagamentoUseCase {

    UUID pagar(@Valid Pagamento pagamento);
}
