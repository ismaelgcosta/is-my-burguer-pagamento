package br.com.ismyburguer;

import br.com.ismyburguer.core.queue.EventQueuesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableConfigurationProperties(EventQueuesProperties.class)
public class Application {
    

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
