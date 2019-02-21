package com.kafka.consumer.kafkaconsumer.config;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

@Configuration
public class KafkaConsumerConfig {

//	String bootStrapServer = "localhost:9092";
//	String groupId = "Fifth-application";
//	String topic = "first_topic";
//	
//	@Bean
//	Properties getConsumerProp() {
//		Properties proeprty = new Properties();
//		proeprty.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
//		proeprty.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		proeprty.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		proeprty.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//		proeprty.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		return proeprty;
//	}
//	
//	@Bean
//	KafkaConsumer<String, String> consumer(){
//		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(getConsumerProp());
//		return consumer;
//	}
	
}
