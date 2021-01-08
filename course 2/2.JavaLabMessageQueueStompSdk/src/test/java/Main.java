import ru.itis.javalabmessagequeue.sdk.stomp.configuration.JlmqConfiguration;
import ru.itis.javalabmessagequeue.sdk.stomp.configuration.JlmqConfigurer;
import ru.itis.javalabmessagequeue.sdk.stomp.connector.Connector;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.Consumer;
import ru.itis.javalabmessagequeue.sdk.stomp.producer.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        JlmqConfiguration jlmqConfiguration = JlmqConfiguration.builder()
                .url("ws://localhost:1337/stomp_endpoint")
                .build();
        JlmqConfigurer jlmqConfigurer = new JlmqConfigurer();
        Connector connector = jlmqConfigurer.configureConnector(jlmqConfiguration);
        Producer producer = connector.producer().forQueue("defaultQueue").create();
        List<String> strings = new ArrayList<>();
        strings.add("Hiii");
        strings.add("byeee");
        Consumer consumer = connector.consumer().forQueue("defaultQueue").onReceive(System.err::println).create();
        TestingPojo testingPojo = TestingPojo.builder()
                .integerField(12)
                .stringField("dd")
                .stringListField(strings)
                .build();
       // producer.send(testingPojo);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
