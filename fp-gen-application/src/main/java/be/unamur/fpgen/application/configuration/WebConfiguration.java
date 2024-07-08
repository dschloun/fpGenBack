package be.unamur.fpgen.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return ObjectMapperBuilder.build();
    }
}
