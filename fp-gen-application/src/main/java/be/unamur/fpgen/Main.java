package be.unamur.fpgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to run the application
 */
@SpringBootApplication(scanBasePackages = "be.unamur.fpgen")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
