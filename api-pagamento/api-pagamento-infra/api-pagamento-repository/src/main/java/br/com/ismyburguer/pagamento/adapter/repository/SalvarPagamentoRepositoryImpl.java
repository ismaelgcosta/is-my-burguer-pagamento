package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoModelConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class SalvarPagamentoRepositoryImpl implements SalvarPagamentoRepository {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoToPagamentoModelConverter converter;

    public SalvarPagamentoRepositoryImpl(PagamentoRepository pagamentoRepository,
                                         PagamentoToPagamentoModelConverter converter) {
        this.pagamentoRepository = pagamentoRepository;
        this.converter = converter;
    }

    @Override
    public UUID salvar(Pagamento pagamento) {
        PagamentoModel pagamentoModel = converter.convert(pagamento);
        return pagamentoRepository.save(pagamentoModel).getPagamentoId();
    }
}
