package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AtualizarStatusPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ReprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class AtualizarStatusPagamentoPedidoUseCaseImpl implements AtualizarStatusPagamentoPedidoUseCase {
    private final AprovarPagamentoPedidoUseCase aprovarPagamentoPedidoUseCase;
    private final ReprovarPagamentoPedidoUseCase reprovarPagamentoPedidoUseCase;

    public AtualizarStatusPagamentoPedidoUseCaseImpl(AprovarPagamentoPedidoUseCase aprovarPagamentoPedidoUseCase, ReprovarPagamentoPedidoUseCase reprovarPagamentoPedidoUseCase) {
        this.aprovarPagamentoPedidoUseCase = aprovarPagamentoPedidoUseCase;
        this.reprovarPagamentoPedidoUseCase = reprovarPagamentoPedidoUseCase;
    }

    @Override
    @Transactional
    public void atualizarStatus(Pagamento pagamento, Pagamento.StatusPagamento statusPagamento) {
        if(statusPagamento == Pagamento.StatusPagamento.PAGO) {
            aprovarPagamentoPedidoUseCase.aprovarPagamento(pagamento);
        }

        if(statusPagamento == Pagamento.StatusPagamento.NAO_AUTORIZADO) {
            reprovarPagamentoPedidoUseCase.reprovarPagamento(pagamento);
        }
    }
}
