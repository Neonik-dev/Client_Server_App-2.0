package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;

public class CheckID {
    private CheckID() {
    }

    public static void checkExistsID(String rawID) throws IncorrectDataEntryExceptions {
        try {
            int id = Integer.parseInt(rawID);
            if (!MusicBandCollection.checkExistsID(id)) {
                throw new IncorrectDataEntryExceptions("В коллекции нет элемента с таким id");
            }
        } catch (NumberFormatException e) {
            throw new IncorrectDataEntryExceptions("Аргумент id не является целым числом");
        }
    }
}
