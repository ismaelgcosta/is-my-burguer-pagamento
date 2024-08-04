package br.com.ismyburguer.controlepedido.entity;

import br.com.ismyburguer.core.validation.Validation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ControlePedido implements Validation {

    private ControlePedidoId controlePedidoId;

    @NotNull(message = "Informe o código do Pedido")
    private PedidoId pedidoId;

    private StatusControlePedido statusControlePedido = StatusControlePedido.RECEBIDO;

    private LocalDateTime recebidoEm = LocalDateTime.now();

    private LocalDateTime inicioDaPreparacao;
    private LocalDateTime fimDaPreparacao;

    public ControlePedido(PedidoId pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Getter
    public enum StatusControlePedido {

        RECEBIDO("Recebido"),
        EM_PREPARACAO("Em Preparação"),
        PRONTO("Pronto"),
        RETIRADO("Retirado");

        private final String descricao;

        StatusControlePedido(String descricao) {
            this.descricao = descricao;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class PedidoId {

        @NotNull(message = "Informe o código do Pedido")
        private UUID pedidoId;

        public PedidoId(String pedidoId) {
            this.pedidoId = UUID.fromString(pedidoId);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ControlePedidoId {

        @NotNull(message = "Informe o código de Controle do Pedido")
        private UUID controlePedidoId;

    }

    public Optional<ControlePedidoId> getControlePedidoId() {
        return Optional.ofNullable(controlePedidoId);
    }
}
