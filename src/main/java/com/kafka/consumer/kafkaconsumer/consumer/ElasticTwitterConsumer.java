package com.kafka.consumer.kafkaconsumer.consumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;

@Service
public class ElasticTwitterConsumer {

	String bootStrapServer = "localhost:9092";
	String groupId = "karthik_twitter";
	String topic = "twitter_tweets";


	public void consumerMessage() {

		//Create Client
		RestHighLevelClient client = createClient();

		//Create Consumer
		KafkaConsumer<String, String> consumer = createConsumer(topic);

		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

			System.out.println("No of records*** " + records.count());
			Integer recordCount = records.count();

			BulkRequest bulkRequest = new BulkRequest();
			for (ConsumerRecord<String, String> consumerRecord : records) {

				try {
					String id = jsonConvert(consumerRecord.value());
					//Insert data in Elastic Search
					IndexRequest indexReq = new IndexRequest("twitter", "tweets",id)//id added to make consumer idempotent do not commit duplicate records
							.source(consumerRecord.value(),XContentType.JSON);

					//bulkRequest.add(indexReq);

					//				if(recordCount > 0) {
					//					try {
					//						BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
					//						consumer.commitAsync();
					//					} catch (IOException e) {
					//						e.printStackTrace();
					//					}
					//				}
					//For single request below code
					try {
						IndexResponse response = client.index(indexReq, RequestOptions.DEFAULT);
						String idss = response.getId();
						System.out.println("idss****** " + idss);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public RestHighLevelClient createClient() {

		String hostName = "kakfaconsumer-427824206.ap-southeast-2.bonsaisearch.net";
		String userName = "bfjri5b3q1";
		String password = "ljrqpvstj8";

		final CredentialsProvider credentials = new BasicCredentialsProvider();
		credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		RestClientBuilder builder = RestClient.builder(
				new HttpHost(hostName, 443, "https"))
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentials);
					}
				});	

		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}


	public KafkaConsumer<String, String> createConsumer(String topic){

		Properties proeprty = new Properties();
		proeprty.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		proeprty.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		proeprty.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		proeprty.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		proeprty.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//proeprty.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //Disables auto commit
		//proeprty.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100"); //wait till 10 records are consumed

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(proeprty);
		consumer.subscribe(Arrays.asList(topic));

		return consumer;
	}


	public String jsonConvert(String jsonTweet) {
		JsonParser parser = new JsonParser();
		return parser.parse(jsonTweet)
				.getAsJsonObject()
				.get("id_str")
				.getAsString();
	}



}
