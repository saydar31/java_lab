package ru.itis.contractgenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.itis.contractgenerator.controllers.ReceiverController;
import ru.itis.contractgenerator.controllers.UserDataController;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class ContractGeneratorApplication implements CommandLineRunner {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Autowired
    private UserDataController userDataController;
    @Autowired
    private ReceiverController receiverController;

    public static void main(String[] args) {
        SpringApplication.run(ContractGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        receiverController.initializeReceivers(input);
        input=scanner.nextLine();
        while (!input.equals("/exit")) {
            userDataController.makeContracts(input);
            input = scanner.nextLine();
        }
        log.info("program finished");
    }
}
