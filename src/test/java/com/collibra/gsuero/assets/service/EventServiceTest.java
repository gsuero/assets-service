package com.collibra.gsuero.assets.service;

import com.collibra.gsuero.assets.model.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {

    private EventService eventService;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup(@Mock RabbitTemplate template) {
        this.eventService = new EventService();
        this.objectMapper = new ObjectMapper();
        this.rabbitTemplate = template;

        this.eventService.setRabbitTemplate(template);
        this.eventService.setObjectMapper(this.objectMapper);
        this.eventService.setAssetsOperationsQueue("assets.operations");


    }

    @BeforeEach
    void init() {
        clearInvocations(this.rabbitTemplate);
    }

    @Test
    void testReceiveMessageFromAssetOperations() {
        // does nothing
        eventService.receiveMessageFromAssetOperations("a message");
    }

    @Test
    void testSendMessage() {
        eventService.sendEvent(buildAsset(1L, "print-me"));
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate, times(1)).convertAndSend(
                eq("assets.operations"), stringArgumentCaptor.capture()
        );

        assertEquals("{\"id\":1,\"name\":\"print-me\",\"parent\":null,\"promoted\":false}", stringArgumentCaptor.getValue());
    }
    private Asset buildAsset(long id, String name) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setId(id);
        asset.setPromoted(false);
        return asset;
    }
}
