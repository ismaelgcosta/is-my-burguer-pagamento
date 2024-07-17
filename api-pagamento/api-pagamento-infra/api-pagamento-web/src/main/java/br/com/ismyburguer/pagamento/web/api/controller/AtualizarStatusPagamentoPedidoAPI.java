package br.com.ismyburguer.pagamento.web.api.controller;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AtualizarStatusPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Pagamentos", description = "Gerenciamento de Pagamentos")
@WebAdapter
@RequestMapping("/pagamentos")
public class AtualizarStatusPagamentoPedidoAPI {

    private final AtualizarStatusPagamentoPedidoUseCase useCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;

    public AtualizarStatusPagamentoPedidoAPI(AtualizarStatusPagamentoPedidoUseCase useCase, ConsultarPagamentoUseCase consultarPagamentoUseCase) {
        this.useCase = useCase;
        this.consultarPagamentoUseCase = consultarPagamentoUseCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Aprovar/Reprovar Pagamento")
    @PutMapping("/{pagamentoId}/{statusPagamento}")
    public void alterarPagamento(@PathVariable @Valid @UUID(message = "O código do pagamento informado está num formato inválido") String pagamentoId, @PathVariable StatusPagamento statusPagamento) {
        Pagamento pagamento = consultarPagamentoUseCase.consultar(pagamentoId);
        useCase.atualizarStatus(pagamento, Pagamento.StatusPagamento.valueOf(statusPagamento.name()));
    }

}
