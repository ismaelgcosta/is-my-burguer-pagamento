package br.com.ismyburguer.controlepedido.adapter.interfaces.in;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface GerarControlePedidoUseCase {
    UUID receberPedido(@Valid @NotNull(message = "Informe o pedido") ControlePedido.PedidoId pedidoId);
}
