package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ReprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class ReprovarPagamentoPedidoUseCaseImpl implements ReprovarPagamentoPedidoUseCase {
    private final SalvarPagamentoRepository repository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    public ReprovarPagamentoPedidoUseCaseImpl(SalvarPagamentoRepository repository, AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.repository = repository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    @Transactional
    public void reprovarPagamento(@Valid Pagamento pagamento) {
        pagamento.validate();
        pagamento.naoAutorizado();
        repository.salvar(pagamento);

        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pagamento.getPedidoId().getPedidoId()), Pedido.StatusPedido.PAGAMENTO_NAO_AUTORIZADO);
    }
}
