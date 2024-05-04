package br.com.ismyburguer.pagamento.web.api.enumeration;

import lombok.Getter;

@Getter
public enum TipoPagamento {

    QR_CODE("QR Code");

    private final String descricao;

    TipoPagamento(String descricao) {
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
