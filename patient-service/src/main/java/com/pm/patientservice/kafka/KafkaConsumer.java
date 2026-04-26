package com.pm.patientservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "patient-group")
    public void consume(byte[] message) {
        try {
            PatientEvent event = PatientEvent.parseFrom(message);
            log.info("Received event: id={}, name={}, email={}, type={}",
                    event.getPatientId(),
                    event.getName(),
                    event.getEmail(),
                    event.getEventType());

        } catch (Exception e) {
            log.error("Error consuming message", e);
        }
    }
}