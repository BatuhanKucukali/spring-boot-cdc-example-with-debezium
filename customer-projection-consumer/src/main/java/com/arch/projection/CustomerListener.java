package com.arch.projection;

import com.arch.projection.message.DebeziumMessage;
import com.arch.projection.message.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerListener {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "master-db.public.customer", groupId = "listener-1")
    void customerListener(@Payload DebeziumMessage<CustomerDto> message,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                          @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [{}] from partition-{} with offset-{}", message, partition, offset);

        CustomerDto customerBeforeDto = objectMapper.convertValue(message.getPayload().getBefore(), CustomerDto.class);
        CustomerDto customerAfterDto = objectMapper.convertValue(message.getPayload().getAfter(), CustomerDto.class);

        Operation operation = message.getPayload().getOp();

        switch (operation) {
            case C:
                create(customerAfterDto);
                break;
            case U:
                update(customerAfterDto);
                break;
            case D:
                delete(customerBeforeDto);
                break;
            default:
                log.info("Undefined operation. Skipped");
                break;
        }

    }

    private void create(CustomerDto customerDto) {
        log.debug("Create a new customer: {}", customerDto);
        customerRepository.save(new Customer(customerDto.getId(), customerDto.getFirstName(), customerDto.getLastName(), customerDto.getEmail()));
    }

    private void update(CustomerDto customerDto) {
        log.debug("Update customer: {}", customerDto);
        Customer customer = customerRepository.findById(customerDto.getId()).orElseThrow(IllegalAccessError::new);
        customer.setFirstName(customer.getFirstName());
        customer.setLastName(customer.getLastName());
        customer.setEmail(customer.getEmail());
        customerRepository.save(customer);
    }

    private void delete(CustomerDto customerDto) {
        log.debug("Delete customer: {}", customerDto);
        customerRepository.findById(customerDto.getId())
                .ifPresent(c -> customerRepository.deleteById(c.getId()));
    }
}
