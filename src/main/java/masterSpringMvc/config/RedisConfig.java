package masterSpringMvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("10.0.0.110");
        return factory;
    }
}
