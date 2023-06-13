package ru.itmo.edu.sppo.lab6;

import ru.itmo.edu.sppo.lab6.utils.FillCollection;
import ru.itmo.edu.sppo.lab6.database.RunMigrations;
import ru.itmo.edu.sppo.lab6.server.Server;

public class ServerApplication {
    private ServerApplication() {
    }

    public static void main(String[] args) {
        RunMigrations.run();
        FillCollection.fillFromDB();
        try (Server server = new Server()) {
            server.start();
        }
    }
}
