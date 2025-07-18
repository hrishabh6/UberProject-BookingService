package com.hrishabh.uberprojectbookingservice.Consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

public class KafkaConsumerService {

    @KafkaListener(topics="sampleTopic")
    public void listen(String message){
        System.out.println("Kafka Message from topic inside booking service" + " : "+message);
    }

}
