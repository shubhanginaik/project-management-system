package fs19.java.backend.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue generalQueue() {
        return new Queue("generalQueue", false);
    }

    @Bean
    public DirectExchange generalExchange() {
        return new DirectExchange("generalExchange");
    }

    @Bean
    public Binding generalBinding(Queue generalQueue, DirectExchange generalExchange) {
        return BindingBuilder.bind(generalQueue).to(generalExchange).with("generalRoutingKey");
    }
}
