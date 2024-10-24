package com.yoyo.event.domain.event.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String TRANSACTION_TOPIC = "transaction-topic";
    private final String MEMBER_TOPIC = "event-member-name-topic";
    private final String CREATE_NOTIFICATION = "create-notification";
    private final String GET_RELATION_IDS = "get-relations-ids";
    private final String SEND_EVENT_NAME = "send-event-name";
    private final String SEND_EVENT_INFO = "send-event-info";

    public void sendEventId(AmountRequestDTO event) {
        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }

    public void getMemberName(MemberRequestDTO event) {
        kafkaTemplate.send(MEMBER_TOPIC, event);
    }

    public void sendEventNotification(NotificationCreateDTO request) {
        kafkaTemplate.send(CREATE_NOTIFICATION, request);
    }

    public void getRelationIds(MemberRequestDTO request){
        kafkaTemplate.send(GET_RELATION_IDS, request);
    }

    public void sendEventNameToTransaction(EventResponseDTO response) {
        kafkaTemplate.send(SEND_EVENT_NAME, response);
    }

    public void sendReceiverId(ReceiverRequestDTO requestDTO) {
        kafkaTemplate.send("receiverId-eventId", requestDTO);
    }

    public void sendEventInfo(EventInfoResponseDTO response) {
        kafkaTemplate.send(SEND_EVENT_INFO, response);
    }
}
