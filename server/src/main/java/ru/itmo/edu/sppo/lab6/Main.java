package ru.itmo.edu.sppo.lab6;

import ru.itmo.edu.sppo.lab6.document.ReadXml;
import ru.itmo.edu.sppo.lab6.server.Server;

import javax.xml.stream.XMLStreamException;

public class Main {
    private static final String INPUT_FILE_NAME_FROM_ENV = "INPUT_FILE";

    private Main() {
    }

    public static void main(String[] args) {
        readCollection();
        Server server = new Server();
        server.start();
        server.stop();
    }

    private static void readCollection() {
        try {
            new ReadXml().readFile(System.getenv().get(INPUT_FILE_NAME_FROM_ENV));
        } catch (XMLStreamException e) {
            System.out.println("Не удалось закрыть соединение с файлом");
        } catch (NullPointerException e) {
            System.out.println("Имя входного файла, нет в переменной окружения");
        }
    }
}
