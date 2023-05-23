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
import java.util.stream.Collectors;

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

    public static void clear() {
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
        if (musicBandCollection.isEmpty()) {
            printer.println("В коллекции пока ничего нет");
        } else {
            musicBandCollection.forEach(
                    (musicBand) -> printer.println(musicBand.toString())
            );
        }
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
        for (MusicBand elem : musicBandCollection) {
            if (elem.getId() == id) {
                musicBandCollection.remove(elem);
                return elem;
            }
        }
        return null;
    }

    public static void delete(int id) {
        getAndDeleteMusicBandById(id);
        ALL_ID.remove(id);
    }

    public static void delete(Printer printer) {
        if (musicBandCollection.isEmpty()) {
            printer.println("Коллекция пустая, удалять нечего.");
        } else {
            int id = Collections.min(ALL_ID);
            MusicBand musicBand = getAndDeleteMusicBandById(id);
            printer.println(musicBand.toString());
            ALL_ID.remove(id);
        }
    }

    public static void getUniqueNumberOfParticipants(Printer printer) {
        Set<Long> uniqueParticipants = musicBandCollection.stream()
                .filter(
                        musicBand -> musicBand.getSafeNumberOfParticipants().isPresent()
                ).map(
                        musicBand -> musicBand.getSafeNumberOfParticipants().get()
                ).collect(Collectors.toSet());
        printer.println(uniqueParticipants.toString());
    }

    public static long countNumberOfParticipants(long number) {
        return musicBandCollection.stream()
                .filter(
                        musicBand -> (musicBand.getSafeNumberOfParticipants().isPresent()) &&
                                (musicBand.getSafeNumberOfParticipants().get() < number)
                ).count();
    }
}
