import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.itis.javalabmessagequeue.sdk.consumer.ReceiveHandler;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
@AllArgsConstructor
public class TestingHandler implements ReceiveHandler {
    private ObjectMapper objectMapper;

    private String randomAlphaNumeric() {
        String result;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            String supportedChars = "abcdefghiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
            stringBuilder.append(supportedChars.charAt(Math.abs(random.nextInt()) % supportedChars.length()));
        }
        result = stringBuilder.toString();
        return result;
    }

    @SneakyThrows
    @Override
    public void handle(JlmqReceiveMessage receiveMessage) {
        System.out.println(randomAlphaNumeric());
        System.err.println(receiveMessage);
        long time = System.currentTimeMillis();
        System.out.println(time);
    }
}
