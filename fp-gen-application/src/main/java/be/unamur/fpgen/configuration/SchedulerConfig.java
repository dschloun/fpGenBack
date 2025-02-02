package be.unamur.fpgen.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for the scheduler. => for batches which run on a periodic basis
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
}
