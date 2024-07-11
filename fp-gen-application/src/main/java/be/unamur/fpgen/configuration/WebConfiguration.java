package be.unamur.fpgen.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"be.unamur.fpgen"})
public class WebConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return ObjectMapperBuilder.build();
    }
}
