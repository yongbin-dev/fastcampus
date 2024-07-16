package com.yb.spring_redis.config;


import com.yb.spring_redis.service.MessageListenerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.*;

@Configuration
public class RedisMessageConfig {

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new MessageListenerService());
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter listener){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        Map<MessageListenerAdapter , Collection<? extends Topic>> map = new HashMap<>();
        List<Topic> topics = new ArrayList<>();
        Topic topic = new ChannelTopic("users:unregister");
        topics.add(topic);
        map.put(listener , topics);

        container.setMessageListeners(map);
        return container;
    }
}
