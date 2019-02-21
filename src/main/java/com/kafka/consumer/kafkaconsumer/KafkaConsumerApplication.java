package com.kafka.consumer.kafkaconsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.kafka.consumer.kafkaconsumer.consumer.ElasticTwitterConsumer;

@SpringBootApplication
public class KafkaConsumerApplication implements CommandLineRunner{

	String bootStrapServer = "localhost:9092";
	String groupId = "Fifth-application";
	String topic = "first_topic";
	
	@Autowired
	private ElasticTwitterConsumer consumer;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
//		Properties proeprty = new Properties();
//		proeprty.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
//		proeprty.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		proeprty.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		proeprty.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//		proeprty.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		
//		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(proeprty);
//		
//		consumer.subscribe(Arrays.asList("first_topic"));
//		//poll for new data
//		while(true) {
//			ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(100));
//			for(ConsumerRecord<String, String> newRec : record) {
//				System.out.println("Messge Receiver " + "key: " + newRec.key() + "value: " + newRec.value());
//			}
//			
//		}
		
		consumer.consumerMessage();
		
	}

}
