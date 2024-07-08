package be.unamur.fpgen.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "be.unamur.fpgen.repository")
@EntityScan(basePackages = "be.unamur.fpgen.entity")
public class PersistenceConfiguration {
}
