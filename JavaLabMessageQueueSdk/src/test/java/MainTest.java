import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.javalabmessagequeue.sdk.configuration.JlmqConfiguration;
import ru.itis.javalabmessagequeue.sdk.configuration.JlmqConfigurer;
import ru.itis.javalabmessagequeue.sdk.connector.Connector;
import ru.itis.javalabmessagequeue.sdk.consumer.Consumer;
import ru.itis.javalabmessagequeue.sdk.producer.Producer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        JlmqConfiguration jlmqConfiguration = JlmqConfiguration.builder()
                .url("ws://localhost:1337/endpoint")
                .build();
        JlmqConfigurer configurer = new JlmqConfigurer();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TestingHandler handler = new TestingHandler(objectMapper);
            Connector connector = configurer.configureConnector(jlmqConfiguration);
            Consumer consumer = connector.consumer().forQueue("defaultQueue").onReceive(handler).create();
            Producer producer = connector.producer().forQueue("defaultQueue").create();
            Scanner scanner = new Scanner(System.in);
            String waiter = scanner.nextLine();
            List<String> names = new ArrayList<>();
            names.add("1");
            names.add("2");
            names.add("3");
            String name = "Sam";
            int age = 18;
            TestingPojo pojo = new TestingPojo(name, age, names);
            producer.send(pojo);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
