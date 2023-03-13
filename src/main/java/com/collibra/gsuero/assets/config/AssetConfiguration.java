package com.collibra.gsuero.assets.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.collibra.gsuero.assets.model")
@EnableJpaRepositories("com.collibra.gsuero.assets")
@EnableTransactionManagement
public class AssetConfiguration {

    private String assetsOperationsQueue;

    @Bean
    public Queue myQueue() {
        return new Queue(assetsOperationsQueue, true);
    }

    @Value("${events.queue.assets-operations}")
    public void setAssetsOperationsQueue(String assetsOperationsQueue) {
        this.assetsOperationsQueue = assetsOperationsQueue;
    }
}
