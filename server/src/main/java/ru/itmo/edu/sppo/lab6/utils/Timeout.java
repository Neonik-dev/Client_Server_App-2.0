package ru.itmo.edu.sppo.lab6.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timeout {
    private static final int TIMEOUT = 100;
    public static void tcpTimeout() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            log.error("Не удалось поспать, для получения всех пакетов от клиента");
        }
    }
}
