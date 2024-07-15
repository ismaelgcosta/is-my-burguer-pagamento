package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.AprovarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class AprovarPagamentoPedidoUseCaseImpl implements AprovarPagamentoPedidoUseCase {
    private final SalvarPagamentoRepository repository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    private final GerarControlePedidoUseCase gerarControlePedidoUseCase;

    public AprovarPagamentoPedidoUseCaseImpl(SalvarPagamentoRepository repository, AlterarStatusPedidoUseCase alterarStatusPedidoUseCase, GerarControlePedidoUseCase gerarControlePedidoUseCase) {
        this.repository = repository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
        this.gerarControlePedidoUseCase = gerarControlePedidoUseCase;
    }

    @Override
    @Transactional
    public void aprovarPagamento(@Valid Pagamento pagamento) {
        try {
            pagamento.validate();
            pagamento.pago();
            repository.salvar(pagamento);
            alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pagamento.getPedidoId().getPedidoId()), Pedido.StatusPedido.PAGO);
            gerarControlePedidoUseCase.receberPedido(new ControlePedido.PedidoId(pagamento.getPedidoId().getPedidoId()));
        } catch (Exception e) {
            alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pagamento.getPedidoId().getPedidoId()), Pedido.StatusPedido.AGUARDANDO_CONFIRMACAO_PAGAMENTO);
            throw e;
        }
    }
}
