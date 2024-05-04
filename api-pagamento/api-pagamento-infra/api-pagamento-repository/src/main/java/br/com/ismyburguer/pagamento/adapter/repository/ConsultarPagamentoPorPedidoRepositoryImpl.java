package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoModelToPagamentoConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoPorPedidoRepository;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoRepository;
import org.hibernate.validator.constraints.UUID;

@PersistenceAdapter
public class ConsultarPagamentoPorPedidoRepositoryImpl implements ConsultarPagamentoPorPedidoRepository {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoModelToPagamentoConverter converter;

    public ConsultarPagamentoPorPedidoRepositoryImpl(PagamentoRepository pagamentoRepository,
                                                     PagamentoModelToPagamentoConverter converter) {
        this.pagamentoRepository = pagamentoRepository;
        this.converter = converter;
    }

    @Override
    public Pagamento consultar(@UUID String pedidoId) {
        PagamentoModel pagamento = pagamentoRepository.findByPedidoId(java.util.UUID.fromString(pedidoId))
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return converter.convert(pagamento);
    }
}
