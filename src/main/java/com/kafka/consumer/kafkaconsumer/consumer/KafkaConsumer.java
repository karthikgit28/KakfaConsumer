package com.kafka.consumer.kafkaconsumer.consumer;

import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

//	@Autowired
//	private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consume;
//	
//	public void receiveMsg() {
//		consume.subscribe(Arrays.asList("first_topic"));
//		//poll for new data
//		while(true) {
//			ConsumerRecords<String, String> record = consume.poll(Duration.ofMillis(100));
//			for(ConsumerRecord<String, String> consumer : record) {
//				System.out.println("Messge Receiver " + "key: " + consumer.key() + "value: " + consumer.value());
//			}
//			
//		}
//	}
	
}
