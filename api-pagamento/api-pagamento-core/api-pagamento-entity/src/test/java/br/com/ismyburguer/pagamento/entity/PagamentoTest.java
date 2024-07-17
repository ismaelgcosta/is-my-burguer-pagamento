package br.com.ismyburguer.pagamento.entity;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class PagamentoTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class);
        GetterSetterVerifier.forClass(Pagamento.class).verify();
        EqualsVerifier.forClass(Pagamento.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Pagamento source = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class);
        new Pagamento(
                source.getPagamentoId(),
                source.getPedidoId(),
                new Pagamento.Total(source.getTotal().getValor()),
                Pagamento.StatusPagamento.valueOf(source.getStatusPagamento().name()),
                Pagamento.FormaPagamento.valueOf(source.getFormaPagamento().name()),
                Pagamento.TipoPagamento.valueOf(source.getTipoPagamento().name()),
                source.getQrCode()
        );
    }
}