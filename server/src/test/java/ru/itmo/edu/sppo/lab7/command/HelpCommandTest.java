package ru.itmo.edu.sppo.lab7.command;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.itmo.edu.sppo.lab6.command.HelpCommand;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {
    private static final ClientRequest request = ClientRequest.builder().commandName("help").argument(new String[]{}).build();

    @Test
    @SneakyThrows
    public void callHelpCommand_OK() {
        // given
        Printer printer = new Printer();

        // when
        ClientResponse response = new HelpCommand().execute(request, printer);

        // then
        assertAll(
                assertNull(response.exception()),
                assertNull(response.exception())
//                assertEquals(response.answer(), );
        );
        System.out.println(response);
    }
}
