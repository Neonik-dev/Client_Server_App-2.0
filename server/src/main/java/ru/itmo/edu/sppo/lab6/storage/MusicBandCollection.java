package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.Commands;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.ValidationMusicBand;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.time.LocalDate;
import java.util.*;

public class MusicBandCollection {
    private static final ValidationMusicBand validationMusicBand;
    private static final HashSet<Integer> ALL_ID = new HashSet<>();
    private static int ID = 0;
    private static final LinkedList<MusicBand> musicBandCollection = new LinkedList<>();
    private static final Date dateCreated = new Date();

    static {
        validationMusicBand = new ValidationMusicBand(new Commands().getAllCommand().keySet());
    }

    public static LinkedList<MusicBand> getMusicBandCollection() {
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

    private static void checkDuplicateID(int id) throws IncorrectDataEntryInFileExceptions {
        if (ALL_ID.contains(id)) {
            throw new IncorrectDataEntryInFileExceptions("Такой id уже существует");
        }
    }

    public static void add(MusicBand musicBand) throws IncorrectDataEntryExceptions, UnexpectedCommandExceptions {
        generatePrivateFields(musicBand);
        validationMusicBand.checkMusicBand(musicBand);
        musicBandCollection.add(musicBand);
//            WalWriter.openFile("add" + "\n" + newInstance.rawData());
    }

    public static void show(Printer printer) {
        Collections.sort(musicBandCollection);
        musicBandCollection.forEach(
                (musicBand) -> printer.println(musicBand.toString())
        );
    }

    public static boolean checkExistsID(int id) {
        return ALL_ID.contains(id);
    }

    public static void updateItem(MusicBand musicBandNew, int id) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions {
        validationMusicBand.checkMusicBand(musicBandNew);
        MusicBand musicBandFromCollection = getAndDeleteMusicBandById(id);
        musicBandCollection.add(mergeMusicBands(musicBandFromCollection, musicBandNew));
    }

    private static MusicBand mergeMusicBands(MusicBand musicBandFromCollection, MusicBand musicBandNew) {
        musicBandNew.setId(musicBandFromCollection.getId());
        musicBandNew.setCreationDate(musicBandFromCollection.getCreationDate());
        return musicBandNew;
    }

    private static MusicBand getAndDeleteMusicBandById(int id) {
        MusicBand musicBand = null;
        for (MusicBand elem : musicBandCollection) {
            if (elem.getId() == id) {
                musicBand = elem;
                musicBandCollection.remove(elem);
                break;
            }
        }
        return musicBand;
    }
}
