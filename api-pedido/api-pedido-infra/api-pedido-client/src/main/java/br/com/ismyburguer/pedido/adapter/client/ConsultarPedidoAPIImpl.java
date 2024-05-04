package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapter.converter.PedidoResponseToPedidoConverter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoAPI;
import feign.FeignException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@WebAdapter
public class ConsultarPedidoAPIImpl implements ConsultarPedidoAPI {
    private final PedidoAPI pedidoAPI;
    private final PedidoResponseToPedidoConverter converter;

    public ConsultarPedidoAPIImpl(FeignClientAPI feignClientAPI,
                                  PedidoResponseToPedidoConverter converter) {
        this.pedidoAPI = feignClientAPI.createClient(PedidoAPI.class);
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Pedido> obterPeloPedidoId(UUID pedidoId) {
        try {
            return Optional.ofNullable(converter.convert(pedidoAPI.findById(pedidoId)));
        } catch (FeignException e) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
    }

}
