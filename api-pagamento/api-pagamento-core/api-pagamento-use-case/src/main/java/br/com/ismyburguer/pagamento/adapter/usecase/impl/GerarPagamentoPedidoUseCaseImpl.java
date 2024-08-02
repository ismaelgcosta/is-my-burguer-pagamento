package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.GerarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.UUID;

@UseCase
public class GerarPagamentoPedidoUseCaseImpl implements GerarPagamentoPedidoUseCase {
    private final SalvarPagamentoRepository pagamentoRepository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    public GerarPagamentoPedidoUseCaseImpl(SalvarPagamentoRepository pagamentoRepository, AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.pagamentoRepository = pagamentoRepository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    public UUID iniciarPagamento(Pagamento pagamento) {
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pagamento.getPedidoId().getPedidoId()), Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);
        pagamento.validate();
        pagamento.aguardandoConfirmacao();
        pagamento.setQrCode("00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D");
        return pagamentoRepository.salvar(pagamento);
    }

}
