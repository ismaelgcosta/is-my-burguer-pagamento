package br.com.ismyburguer.pagamento.adapter.model;

import lombok.Getter;

@Getter
public enum StatusPagamento {

    AGUARDANDO_CONFIRMACAO,
    NAO_AUTORIZADO,
    PAGO;

}

