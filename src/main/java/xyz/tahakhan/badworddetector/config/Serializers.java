package xyz.tahakhan.badworddetector.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Serializers {
    @Bean
    public Gson getGson() {
        return new Gson();
    }
}
