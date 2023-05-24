package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

import java.io.File;

public class CheckFile {
    private static final String WRONG_PATH_TEXT = "Путь %s указывает не на файл";
    private static final String FORBIDDEN_TO_WRITE_TEXT = "В файл невозможно произвести запись, недостаточно прав";
    private static final String FORBIDDEN_TO_READ_TEXT = "Файл невозможно прочитать, недостаточно прав";

    public static void checkFileForRead(String fileName) throws IncorrectDataEntryExceptions {
        File file = new File(fileName);
        checkIsFile(file, fileName);
        if (!file.canRead()) {
            throw new IncorrectDataEntryExceptions(FORBIDDEN_TO_READ_TEXT);
        }
    }

    public static void checkFileForWrite(String fileName) throws IncorrectDataEntryExceptions {
        File file = new File(fileName);
        if (file.exists()) {
            checkIsFile(file, fileName);
            if (!file.canWrite()) {
                throw new IncorrectDataEntryExceptions(FORBIDDEN_TO_WRITE_TEXT);
            }
        }
    }

    private static void checkIsFile(File file, String fileName) throws IncorrectDataEntryExceptions {
        if (!file.isFile()) {
            throw new IncorrectDataEntryExceptions(String.format(WRONG_PATH_TEXT, fileName));
        }
    }
}