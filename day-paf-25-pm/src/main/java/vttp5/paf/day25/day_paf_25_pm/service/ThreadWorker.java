package vttp5.paf.day25.day_paf_25_pm.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class ThreadWorker implements Runnable{

    @Autowired
    @Qualifier("myredis")
    private RedisTemplate<String,String> template;

    private String name;

    public ThreadWorker(RedisTemplate<String, String> template, String name) {
        this.template = template;
        this.name = name;
    }


    @Override
    public void run() {
        // day 25 slide 10
        ListOperations<String,String> listOps = template.opsForList();

        while (true) {
            try {
                Optional<String> option = Optional.ofNullable(listOps.rightPop("myqueue", Duration.ofSeconds(30)));

                if (option.isEmpty()) {
                    continue;
                }

                String payload = option.get();
                System.out.println(payload);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

     }
    
}
