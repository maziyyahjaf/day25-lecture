package vttp5.paf.day25.day_paf_25_producer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp5.paf.day25.day_paf_25_producer.model.Order;
import vttp5.paf.day25.day_paf_25_producer.model.Student;
import vttp5.paf.day25.day_paf_25_producer.model.Todo;

@Configuration
public class RedisConfig {

    // topic
    @Value("${redis.topic3}")
    String orderTopic;

    @Bean("todo")
    RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac, 
                Jackson2JsonRedisSerializer<Todo> serializer) {
            RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connFac);
            redisTemplate.setDefaultSerializer(serializer);
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }
    
    @Bean("student")
    RedisTemplate<String, Student> studentRedisTemplate(RedisConnectionFactory connFac, @Qualifier("student-serializer")Jackson2JsonRedisSerializer<Student> serializer) {
            RedisTemplate<String, Student> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connFac);
            redisTemplate.setDefaultSerializer(serializer);
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
    }

    @Bean("student-serializer")
    public Jackson2JsonRedisSerializer<Student> studentJackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Student.class);
    }

    @Bean("order")
    RedisTemplate<String, Order> orderRedisTemplate(RedisConnectionFactory connFac) {
            RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connFac);
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(orderTopic);
    }
}
