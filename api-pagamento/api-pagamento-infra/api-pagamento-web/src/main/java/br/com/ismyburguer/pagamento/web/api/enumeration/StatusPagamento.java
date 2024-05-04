package br.com.ismyburguer.pagamento.web.api.enumeration;

import lombok.Getter;

@Getter
public enum StatusPagamento {

    AGUARDANDO_CONFIRMACAO("Aguardando Confirmação do Pagamento"),
    NAO_AUTORIZADO("Não Autorizado"),
    PAGO("Pago");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

