package vttp5.paf.day25.day_paf_25_consumer.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

// import java.io.StringReader;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
// import jakarta.json.Json;
// import jakarta.json.JsonObject;
// import jakarta.json.JsonReader;
import vttp5.paf.day25.day_paf_25_consumer.model.Order2;
import vttp5.paf.day25.day_paf_25_consumer.model.Student;
import vttp5.paf.day25.day_paf_25_consumer.model.Todo;
import vttp5.paf.day25.day_paf_25_consumer.utils.Utils;

@Service
public class ConsumerService implements MessageListener {

    @Value("${order.local.url}")
    private String orderLocalUrl;

    private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    public void handleMessage(Todo todo) {
        // can save to db
        System.out.println(todo);
    }

    public void handleMessage(Student student) {
        // can save to db
        System.out.println(student);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String orderData = new String(message.getBody());
            System.out.println(orderData);

            // using Json-P to map it back to object
            Order2 order = Utils.toOrder2(orderData);
            logger.info("Order data sending to day 24 ws: {}", order);
            // call the API in day 24 using RestTemplate to write to MySQL database
            RestTemplate restTemplate = new RestTemplate();
            //build the request entity
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            JsonObject order2JsonObj = Utils.toJsonObject2(order);
            
            RequestEntity<String> request = RequestEntity
                                            .post(URI.create(orderLocalUrl))
                                            .headers(headers)
                                            .body(order2JsonObj.toString());
            try {
                ResponseEntity<String> response = restTemplate.exchange(request, String.class);
                System.out.println(response);
            } catch (HttpClientErrorException ex) {
                System.err.println(ex.getMessage());
            }


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

  
    
}
