package vttp5.paf.day25.day_paf_25_consumer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp5.paf.day25.day_paf_25_consumer.model.Order;
import vttp5.paf.day25.day_paf_25_consumer.model.Student;
import vttp5.paf.day25.day_paf_25_consumer.model.Todo;
import vttp5.paf.day25.day_paf_25_consumer.service.ConsumerService;

@Configuration
public class RedisConfig {

    @Value("${redis.topic1}")
    private String redisTopic; // subscribed message defined in application.properties

    @Value("${redis.topic2}")
    private String studentTopic;

    @Value("${redis.topic3}")
    String orderTopic;

    @Bean
    public RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac,
            Jackson2JsonRedisSerializer<Todo> serializer) {
        RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Student> studentRedisTemplate(RedisConnectionFactory connFac,
            @Qualifier("student-serializer") Jackson2JsonRedisSerializer<Student> serializer) {
        RedisTemplate<String, Student> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }

    @Bean("student-serializer")
    public Jackson2JsonRedisSerializer<Student> studentJackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Student.class);
    }

    @Bean
    public RedisMessageListenerContainer listenerContainer(
            @Qualifier("student-listener-adapter") MessageListenerAdapter messageListenerAdapter,
            RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic));

        return container;
    }

    @Bean("todo-listener-adapter")
    public MessageListenerAdapter listenerAdapter(ConsumerService consumerService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(consumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));

        return adapter;
    }

    @Bean("student-listener-container")
    public RedisMessageListenerContainer studentListenerContainer(
            @Qualifier("student-listener-adapter") MessageListenerAdapter messageListenerAdapter,
            RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(studentTopic));

        return container;
    }

    @Bean("student-listener-adapter")
    public MessageListenerAdapter studentListenerAdapter(ConsumerService consumerService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(consumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Student.class));

        return adapter;
    }

    @Bean
    public RedisTemplate<String, Order> orderRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(orderTopic);
    }

    // private MessageListener messageListener;
    // private RedisConnectionFactory redisConnectionFactory;

    @Bean("order-listener-container")
    public RedisMessageListenerContainer orderListenerContainer(@Qualifier("order-listener-adapter") MessageListenerAdapter messageListenerAdapter,
    RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(orderTopic));

        return container;
    }

    @Bean("order-listener-adapter")
    public MessageListenerAdapter orderListenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService);
    }
}
