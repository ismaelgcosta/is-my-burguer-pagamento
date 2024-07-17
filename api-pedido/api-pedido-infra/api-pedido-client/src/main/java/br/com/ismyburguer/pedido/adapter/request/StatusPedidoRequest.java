package br.com.ismyburguer.pedido.adapter.request;

public enum StatusPedidoRequest {

    ABERTO,
    FECHADO,
    AGUARDANDO_PAGAMENTO,
    AGUARDANDO_CONFIRMACAO_PAGAMENTO,
    PAGO,
    PAGAMENTO_NAO_AUTORIZADO,
    CANCELADO,
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;
}
