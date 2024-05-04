package br.com.ismyburguer.pagamento.web.api.controller;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.PagarPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Pagamentos", description = "Gerenciamento de Pagamentos")
@WebAdapter
@RequestMapping("/pagamentos")
public class PagarPedidoAPI {
    private final PagarPedidoUseCase useCase;

    public PagarPedidoAPI(PagarPedidoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"), description = "Consultar Pagamento")
    @PostMapping("/{pedidoId}")
    public String pagar(@PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId) {
        return useCase.pagar(new Pagamento.PedidoId(pedidoId));
    }

}
