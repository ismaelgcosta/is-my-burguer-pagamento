package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoPorPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoPorPedidoRepository;
import org.hibernate.validator.constraints.UUID;

@UseCase
public class ConsultaPagamentoPorPedidoUseCaseImpl implements ConsultarPagamentoPorPedidoUseCase {
    private final ConsultarPagamentoPorPedidoRepository repository;
    public ConsultaPagamentoPorPedidoUseCaseImpl(ConsultarPagamentoPorPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento consultar(@UUID String pedidoId) {
        return repository.consultar(pedidoId);
    }
}
