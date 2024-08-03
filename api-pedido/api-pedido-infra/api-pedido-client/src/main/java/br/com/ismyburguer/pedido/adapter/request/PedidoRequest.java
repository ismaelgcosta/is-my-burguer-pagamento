package br.com.ismyburguer.pedido.adapter.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoRequest {

    UUID pedidoId;
    StatusPedidoRequest statusPedido;
}
