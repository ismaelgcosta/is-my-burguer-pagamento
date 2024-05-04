package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoModelToPagamentoConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoRepository;
import org.hibernate.validator.constraints.UUID;

@PersistenceAdapter
public class ConsultarPagamentoRepositoryImpl implements ConsultarPagamentoRepository {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoModelToPagamentoConverter converter;

    public ConsultarPagamentoRepositoryImpl(PagamentoRepository pagamentoRepository,
                                            PagamentoModelToPagamentoConverter converter) {
        this.pagamentoRepository = pagamentoRepository;
        this.converter = converter;
    }

    @Override
    public Pagamento consultar(@UUID String pagamentoId) {
        PagamentoModel pagamento = pagamentoRepository.findById(java.util.UUID.fromString(pagamentoId))
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return converter.convert(pagamento);
    }
}
