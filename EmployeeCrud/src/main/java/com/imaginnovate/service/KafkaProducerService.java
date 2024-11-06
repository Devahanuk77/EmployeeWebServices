package com.imaginnovate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
	
	private static final String TOPIC_NAME ="employee_topic";
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public void sendEmployeeRecord(Object employee) {
		kafkaTemplate.send(TOPIC_NAME, employee);
	}
	

}
