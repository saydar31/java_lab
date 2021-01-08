package ru.itis.javalabmessagequeue.parameters;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Parameters(commandNames = "authentication",
        commandDescription = "set authentication key"
        ,separators = "=")
public class AuthenticationKeyParameter {
    @Parameter(names = {"-k","-key"}, description = "key",required = true)
    private String  key;
}
