package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoModelConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoRepository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class EfetuarPagamentoRepositoryImpl implements EfetuarPagamentoRepository {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoToPagamentoModelConverter converter;

    public EfetuarPagamentoRepositoryImpl(PagamentoRepository pagamentoRepository,
                                          PagamentoToPagamentoModelConverter converter) {
        this.pagamentoRepository = pagamentoRepository;
        this.converter = converter;
    }

    @Override
    public UUID pagar(Pagamento pagamento) {
        PagamentoModel pagamentoModel = converter.convert(pagamento);
        return pagamentoRepository.save(pagamentoModel).getPagamentoId();
    }
}
