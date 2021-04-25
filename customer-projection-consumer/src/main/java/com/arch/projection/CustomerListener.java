package com.arch.projection;

import com.arch.projection.message.DebeziumMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerListener {

    @KafkaListener(topics = "dbserver1.public.customers", groupId = "group_id")
    void customerListener(@Payload DebeziumMessage<CustomerDto> message,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                          @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [{}] from partition-{} with offset-{}", message, partition, offset);
    }
}
