package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoRepository;
import jakarta.validation.Valid;

import java.util.UUID;

@UseCase
public class EfetuarPagamentoUseCaseImpl implements EfetuarPagamentoUseCase {
    private final EfetuarPagamentoRepository repository;
    public EfetuarPagamentoUseCaseImpl(EfetuarPagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID pagar(@Valid Pagamento pagamento) {
        pagamento.validate();
        pagamento.pago();
        pagamento.setQrCode("00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D");
        return repository.pagar(pagamento);
    }
}
