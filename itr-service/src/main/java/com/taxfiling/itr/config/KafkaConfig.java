
package com.taxfiling.itr.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name("notifications").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic auditLogTopic() {
        return TopicBuilder.name("audit-logs").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic paymentStatusTopic() {
        return TopicBuilder.name("payment-status").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic documentProcessingTopic() {
        return TopicBuilder.name("document-processing").partitions(3).replicas(1).build();
    }
}
