package com.springboot.employeeManagement.kafka;

import com.springboot.employeeManagement.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final String Topic = "EmployeeTopic";

    public String show(@RequestBody Employee emp) {
        this.kafkaTemplate.send(Topic, emp);
        return "Check your Console";
    }

    public String show_1(@RequestBody String str) {
        this.kafkaTemplate.send(Topic, str);
        return "Check your Console";
    }
}
