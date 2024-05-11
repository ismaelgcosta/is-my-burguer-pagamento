package br.com.ismyburguer.controlepedido.gateway.out;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import jakarta.validation.Valid;

import java.util.UUID;

public interface GerarControlePedidoAPI {

    UUID gerar(@Valid ControlePedido.PedidoId controlepedido);

}
