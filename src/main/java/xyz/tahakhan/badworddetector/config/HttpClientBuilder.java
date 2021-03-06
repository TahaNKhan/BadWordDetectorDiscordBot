package xyz.tahakhan.badworddetector.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientBuilder {

    @Bean
    public HttpClient getObject() {
        return HttpClients.createDefault();
    }
}
