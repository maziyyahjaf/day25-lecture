package vttp5.paf.day25.day_paf_25_consumer.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import vttp5.paf.day25.day_paf_25_consumer.model.Student;
import vttp5.paf.day25.day_paf_25_consumer.model.Todo;

@Service
public class ConsumerService implements MessageListener {

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
            // call the API in day 24 using RestTemplate to write to MySQL database
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

  
    
}
