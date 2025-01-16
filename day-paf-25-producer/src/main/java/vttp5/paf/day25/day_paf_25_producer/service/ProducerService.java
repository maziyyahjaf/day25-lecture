package vttp5.paf.day25.day_paf_25_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp5.paf.day25.day_paf_25_producer.model.Order;
import vttp5.paf.day25.day_paf_25_producer.model.Order2;
import vttp5.paf.day25.day_paf_25_producer.model.Student;
import vttp5.paf.day25.day_paf_25_producer.model.Todo;
import vttp5.paf.day25.day_paf_25_producer.utils.Utils;

@Service
public class ProducerService {

    @Autowired
    @Qualifier("todo")
    RedisTemplate<String, Todo> redisTemplate;

    @Autowired
    @Qualifier("student")
    RedisTemplate<String, Student> template;

    @Autowired
    @Qualifier("order")
    RedisTemplate<String, Order> orderTemplate;

    @Value("${redis.topic1}")
    private String topic1;

    @Value("${redis.topic2}")
    private String topic2;

    @Autowired
    ChannelTopic channelTopic;

    public void sendMessage(Todo todo) {
        redisTemplate.convertAndSend(topic1, todo);
    }

    public void sendMessage(Student student) {
        template.convertAndSend(topic2, student);
    }

    public Long publish(String rawJsonString) {
        try {
            // Convert JSON string to Order object
            Order order = Utils.toOrder(rawJsonString);

            // Validate Order fields (if necessary)
            validateOrder(order);

            // Serialize back to JSON string for sending
            JsonObject orderJson = Utils.toJsonObject(order);
            return orderTemplate.convertAndSend(channelTopic.getTopic(), orderJson.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error processing order JSON", e);
        }
    }

    // Validation example
    private void validateOrder(Order order) {
        if (order.getFullName() == null || order.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Order fullName cannot be null or empty");
        }
        if (order.getLineItem() == null || order.getLineItem().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one line item");
        }
    }

    public Long publish2(String rawJsonString) {
        try {
            // Convert JSON string to Order object
            Order2 order = Utils.toOrder2(rawJsonString);

            // Validate Order fields (if necessary)
            validateOrder2(order);

            // Serialize back to JSON string for sending
            JsonObject orderJson = Utils.toJsonObject2(order);
            return orderTemplate.convertAndSend(channelTopic.getTopic(), orderJson.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error processing order JSON", e);
        }
    }

     // Validation example
     private void validateOrder2(Order2 order) {
        if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Order fullName cannot be null or empty");
        }
        if (order.getLineItem() == null || order.getLineItem().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one line item");
        }
    }
}
