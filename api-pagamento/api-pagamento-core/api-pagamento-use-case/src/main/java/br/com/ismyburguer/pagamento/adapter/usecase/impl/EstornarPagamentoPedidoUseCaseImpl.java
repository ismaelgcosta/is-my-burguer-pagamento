package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoPorPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EstornarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class EstornarPagamentoPedidoUseCaseImpl implements EstornarPagamentoPedidoUseCase {
    private final SalvarPagamentoRepository repository;
    private final ConsultarPagamentoPorPedidoUseCase consultarPagamentoPorPedidoUseCase;

    public EstornarPagamentoPedidoUseCaseImpl(SalvarPagamentoRepository repository,
                                              ConsultarPagamentoPorPedidoUseCase consultarPagamentoPorPedidoUseCase) {
        this.repository = repository;
        this.consultarPagamentoPorPedidoUseCase = consultarPagamentoPorPedidoUseCase;
    }

    @Override
    @Transactional
    public void estornarPagamento(Pagamento.@NotNull PedidoId pedidoId) {
        Pagamento pagamento = consultarPagamentoPorPedidoUseCase.consultar(pedidoId.getPedidoId());
        pagamento.validate();
        pagamento.estornado();
        repository.salvar(pagamento);
    }
}
