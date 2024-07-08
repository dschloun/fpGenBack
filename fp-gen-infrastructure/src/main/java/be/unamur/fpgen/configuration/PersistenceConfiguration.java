package be.unamur.fpgen.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@PropertySource("classpath:db/configuration.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "be.unamur.fpgen.repository")
@EntityScan(basePackages = "be.unamur.fpgen.entity")
@ComponentScan("be.unamur.fpgen")
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class PersistenceConfiguration {
    @Bean
    public DateTimeProvider auditingDateTimeProvider(){
        return () -> Optional.of(OffsetDateTime.now());
    }
}
