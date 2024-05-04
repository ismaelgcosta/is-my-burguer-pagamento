package br.com.ismyburguer.pedido.adapter.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.adapter.response.PedidoResponse;
import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.Optional;

@WebConverter
public class PedidoResponseToPedidoConverter implements Converter<PedidoResponse, Pedido> {

    @Override
    public Pedido convert(PedidoResponse source) {
        return new Pedido(
                new Pedido.PedidoId(source.getPedidoId()),
                Optional.ofNullable(source.getClienteId()).map(Pedido.ClienteId::new).orElse(null),
                Pedido.StatusPedido.valueOf(source.getStatus()),
                source.getValorTotal()
        );
    }
}
