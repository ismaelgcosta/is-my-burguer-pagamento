package br.com.ismyburguer.pedido.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class PedidoResponse {

    private String pedidoId;
    private String clienteId;
    private String status;
    private BigDecimal valorTotal;

}
