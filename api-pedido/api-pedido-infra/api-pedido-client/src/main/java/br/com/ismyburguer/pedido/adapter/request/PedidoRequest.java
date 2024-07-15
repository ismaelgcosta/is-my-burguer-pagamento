package br.com.ismyburguer.pedido.adapter.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    UUID pedidoId;
    StatusPedidoRequest statusPedido;
}
