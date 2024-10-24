package com.yoyo.banking.domain.account.consumer;

import com.yoyo.banking.domain.account.service.PayService;
import com.yoyo.common.kafka.dto.EventInfoResponseDTO;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.MemberResponseDTO;
import com.yoyo.common.kafka.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayConsumer {

    private final String UPDATE_YOYO_PAY_BY_NO_MEMBER = "update-yoyo-pay-by-no-member";
    private final String MEMBER_NAME_TO_PAY_TOPIC = "member-name-to-pay-topic";
    private final String USER_KEY_TO_BANKING_TOPIC = "user-key-to-banking-topic";
    private final String SEND_EVENT_INFO = "send-event-info";

    private final PayService payService;

    @KafkaListener(topics = UPDATE_YOYO_PAY_BY_NO_MEMBER, concurrency = "3")
    public void updatePayForNoMember(PaymentDTO request) {
        payService.noMemberPayment(request);
    }

    @KafkaListener(topics = MEMBER_NAME_TO_PAY_TOPIC, concurrency = "3")
    public void getMemberName(MemberResponseDTO response) {
        payService.completeMemberName(response);
    }

    @KafkaListener(topics = USER_KEY_TO_BANKING_TOPIC, concurrency = "3")
    public void createUserKey(MemberRequestDTO request) {
        payService.createUserKey(request.getMemberId());
    }

    @KafkaListener(topics = SEND_EVENT_INFO, concurrency = "3")
    public void getEventInfo(EventInfoResponseDTO response) {
        payService.completeEventInfo(response);
    }}
