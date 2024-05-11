package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoAPI;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class GerarControlePedidoUseCaseImpl implements GerarControlePedidoUseCase {
    private final GerarControlePedidoAPI gerarControlePedidoAPI;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    public GerarControlePedidoUseCaseImpl(GerarControlePedidoAPI gerarControlePedidoAPI,
                                          AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.gerarControlePedidoAPI = gerarControlePedidoAPI;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    public UUID receberPedido(@Valid ControlePedido.PedidoId pedidoId) {
        UUID controlePedidoId = gerarControlePedidoAPI.gerar(pedidoId);
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.RECEBIDO);
        return controlePedidoId;
    }
}
