package br.com.ismyburguer.pedido.adapter.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoRequest {

    UUID pedidoId;
    StatusPedidoRequest statusPedido;
}
