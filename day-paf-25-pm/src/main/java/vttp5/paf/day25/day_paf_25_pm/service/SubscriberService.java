package vttp5.paf.day25.day_paf_25_pm.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
       
        String data = new String(message.getBody());
        System.out.println(data);
    }
    
}
