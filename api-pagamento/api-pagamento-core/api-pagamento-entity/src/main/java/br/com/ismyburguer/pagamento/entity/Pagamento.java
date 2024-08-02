package br.com.ismyburguer.pagamento.entity;

import br.com.ismyburguer.core.validation.Validation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento implements Validation {

    private PagamentoId pagamentoId;

    @NotNull(message = "Informe o código do Pedido")
    private PedidoId pedidoId;

    @Setter
    @NotNull(message = "Informe o valor Total do Pedido")
    private Total total;

    @Setter
    private StatusPagamento statusPagamento = StatusPagamento.AGUARDANDO_CONFIRMACAO;

    private FormaPagamento formaPagamento = FormaPagamento.MERCADO_PAGO;

    private TipoPagamento tipoPagamento = TipoPagamento.QR_CODE;

    @Setter
    private String qrCode;

    public Pagamento(PedidoId pedidoId, Total total) {
        this.pedidoId = pedidoId;
        this.total = total;
    }

    public void pago() {
        this.statusPagamento = StatusPagamento.PAGO;
    }

    public void estornado() {
        this.statusPagamento = StatusPagamento.ESTORNADO;
    }

    public void naoAutorizado() {
        this.statusPagamento = StatusPagamento.NAO_AUTORIZADO;
    }

    public void aguardandoConfirmacao() {
        this.statusPagamento = StatusPagamento.AGUARDANDO_CONFIRMACAO;
    }

    @Getter
    public enum StatusPagamento {

        AGUARDANDO_CONFIRMACAO("Aguardando Confirmação do Pagamento"),
        NAO_AUTORIZADO("Não Autorizado"),
        ESTORNADO("Estornado"),
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

    @Getter
    public enum FormaPagamento {

        MERCADO_PAGO("Mercado Pago");

        private final String descricao;

        FormaPagamento(String descricao) {
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class PedidoId {

        @NotNull(message = "Informe o código do Pedido")
        private UUID pedidoId;

        public PedidoId(String pedidoId) {
            this.pedidoId = UUID.fromString(pedidoId);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class PagamentoId {

        @NotNull(message = "Informe o código do Pagamento")
        private UUID pagamentoId;

        public PagamentoId(String pagamentoId) {
            this.pagamentoId = UUID.fromString(pagamentoId);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class Total {

        @NotNull(message = "Informe o valor Total do Pedido")
        @DecimalMin(value = "100.00", message = "O valor total do item deve ser de no mínimo R$100,00")
        private BigDecimal valor;

    }

}
