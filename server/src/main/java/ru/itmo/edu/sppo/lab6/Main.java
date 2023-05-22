package ru.itmo.edu.sppo.lab6;

import ru.itmo.edu.sppo.lab6.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.stop();
    }
}
