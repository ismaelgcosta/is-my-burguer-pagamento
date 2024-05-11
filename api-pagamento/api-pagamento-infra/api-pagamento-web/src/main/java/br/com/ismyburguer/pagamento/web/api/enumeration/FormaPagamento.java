package br.com.ismyburguer.pagamento.web.api.enumeration;

import lombok.Getter;

@Getter
public enum FormaPagamento {

    MERCADO_PAGO("Mercado Pago");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

}