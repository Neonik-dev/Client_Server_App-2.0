package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.ValidationMusicBand;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MusicBandCollection {
    private static final ValidationMusicBand VALIDATION_MUSIC_BAND;
    private static final HashSet<Integer> ALL_ID = new HashSet<>();
    private static int idForMusicBand = 0;
    private static final LinkedList<MusicBand> MUSIC_BAND_COLLECTION = new LinkedList<>();
    private static final Date DATE_CREATED = new Date();

    static {
        VALIDATION_MUSIC_BAND = new ValidationMusicBand(new Commands().getAllCommand().keySet());
    }

    private MusicBandCollection() {
    }

    public static LinkedList<MusicBand> getMusicBandCollection() {
        return (LinkedList<MusicBand>) MUSIC_BAND_COLLECTION.clone();
    }

    public static String getDateCreated() {
        return DATE_CREATED.toString();
    }

    public static void clear() {
        MUSIC_BAND_COLLECTION.clear();
        ALL_ID.clear();
    }

    private static int generatorId() {
        while (ALL_ID.contains(++idForMusicBand)) {
        }
        ALL_ID.add(idForMusicBand);
        return idForMusicBand;
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
        VALIDATION_MUSIC_BAND.checkMusicBand(musicBand);
        MUSIC_BAND_COLLECTION.add(musicBand);
    }

    public static void addFromServerFile(MusicBand musicBand) throws IncorrectDataEntryInFileExceptions {
        checkDuplicateID(musicBand.getId());
        ALL_ID.add(musicBand.getId());
        MUSIC_BAND_COLLECTION.add(musicBand);
    }

    public static void show(Printer printer) {
        Collections.sort(MUSIC_BAND_COLLECTION);
        if (MUSIC_BAND_COLLECTION.isEmpty()) {
            printer.println("В коллекции пока ничего нет");
        } else {
            MUSIC_BAND_COLLECTION.forEach(
                    (musicBand) -> printer.println(musicBand.toString())
            );
        }
    }

    public static boolean checkExistsID(int id) {
        return ALL_ID.contains(id);
    }

    public static void updateItem(MusicBand musicBandNew, int id) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions {
        VALIDATION_MUSIC_BAND.checkMusicBand(musicBandNew);
        MusicBand musicBandFromCollection = getAndDeleteMusicBandById(id);
        MUSIC_BAND_COLLECTION.add(mergeMusicBands(musicBandFromCollection, musicBandNew));
    }

    private static MusicBand mergeMusicBands(MusicBand musicBandFromCollection, MusicBand musicBandNew) {
        musicBandNew.setId(musicBandFromCollection.getId());
        musicBandNew.setCreationDate(musicBandFromCollection.getCreationDate());
        return musicBandNew;
    }

    private static MusicBand getAndDeleteMusicBandById(int id) {
        for (MusicBand elem : MUSIC_BAND_COLLECTION) {
            if (elem.getId() == id) {
                MUSIC_BAND_COLLECTION.remove(elem);
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
        if (MUSIC_BAND_COLLECTION.isEmpty()) {
            printer.println("Коллекция пустая, удалять нечего.");
        } else {
            int id = Collections.min(ALL_ID);
            MusicBand musicBand = getAndDeleteMusicBandById(id);
            printer.println(musicBand.toString());
            ALL_ID.remove(id);
        }
    }

    public static void getUniqueNumberOfParticipants(Printer printer) {
        Set<Long> uniqueParticipants = MUSIC_BAND_COLLECTION.stream()
                .filter(
                        musicBand -> musicBand.getSafeNumberOfParticipants().isPresent()
                ).map(
                        musicBand -> musicBand.getSafeNumberOfParticipants().get()
                ).collect(Collectors.toSet());
        printer.println(uniqueParticipants.toString());
    }

    public static long countNumberOfParticipants(long number) {
        return MUSIC_BAND_COLLECTION.stream()
                .filter(
                        musicBand -> musicBand.getSafeNumberOfParticipants().isPresent()
                                && musicBand.getSafeNumberOfParticipants().get() < number
                ).count();
    }
}
