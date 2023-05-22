package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.Commands;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.MusicBandValidation;

import java.time.LocalDate;
import java.util.*;

public class MusicBandCollection {
    private static final MusicBandValidation musicBandValidation;
    private static final HashSet<Integer> ALL_ID = new HashSet<>();
    private static int ID = 0;
    private static final LinkedList<MusicBand> musicBandCollection = new LinkedList<>();
    private static final Date dateCreated = new Date();

    static {
        musicBandValidation = new MusicBandValidation(new Commands().getAllCommand().keySet());
    }

    public static LinkedList<MusicBand> getMusicBandLinkedList() {
        return (LinkedList<MusicBand>) musicBandCollection.clone();
    }

    public static String getDateCreated() {
        return dateCreated.toString();
    }

    public void clear() {
        musicBandCollection.clear();
        ALL_ID.clear();
    }

    private static int generatorId() {
        while (ALL_ID.contains(++ID)) {
        }
        ALL_ID.add(ID);
        return ID;
    }

    private static void generatePrivateFields(MusicBand musicBand) {
        musicBand.setId(generatorId());
        musicBand.setCreationDate(LocalDate.now());
    }

    private static void checkID(int id) throws IncorrectDataEntryInFileExceptions {
        if (ALL_ID.contains(id)) {
            throw new IncorrectDataEntryInFileExceptions("Такой id уже существует");
        }
    }

    public static void add(MusicBand musicBand) throws IncorrectDataEntryExceptions {
        try {
            generatePrivateFields(musicBand);
            musicBandValidation.checkMusicBand(musicBand);
            musicBandCollection.add(musicBand);
//            WalWriter.openFile("add" + "\n" + newInstance.rawData());
        } catch (UnexpectedCommandExceptions e) {
            System.out.println(e.getMessage());
        }
    }
}
