package com.collibra.gsuero.assets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private String assetsOperationsQueue;
    private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"assets.operations"})
    public void receiveMessageFromAssetOperations(String message) {
        LOG.info("Received topic  message: {}", message);
    }

    public void sendEvent(Object object) {
        LOG.debug("Sending event from object...");
        try {
            rabbitTemplate.convertAndSend(assetsOperationsQueue, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            LOG.error("Impossible to send out a message", e);
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${events.queue.assets-operations}")
    public void setAssetsOperationsQueue(String assetsOperationsQueue) {
        this.assetsOperationsQueue = assetsOperationsQueue;
    }
}
