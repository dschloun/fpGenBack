package be.unamur.fpgen.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "be.afelio.hr")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
