package br.com.ismyburguer.core.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventQueuesProperties {

    private String isMyBurguerPagamentoQueue;

    public String getIsMyBurguerPagamentoQueue() {
        return isMyBurguerPagamentoQueue;
    }

    public void setIsMyBurguerPagamentoQueue(String isMyBurguerPagamentoQueue) {
        this.isMyBurguerPagamentoQueue = isMyBurguerPagamentoQueue;
    }
}