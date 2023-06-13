package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.ValidationMusicBand;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MusicBandCollection {
    public static final ValidationMusicBand VALIDATION_MUSIC_BAND;
    private static final HashSet<Integer> ALL_ID = new HashSet<>();
    private static final LinkedList<MusicBand> MUSIC_BAND_COLLECTION = new LinkedList<>();
    private static final Date DATE_CREATED = new Date();
    private static final ReentrantLock locker = new ReentrantLock();

    static {
        VALIDATION_MUSIC_BAND = new ValidationMusicBand(new Commands().getAllCommand().keySet());
    }

    private MusicBandCollection() {
    }

    public static LinkedList<MusicBand> getMusicBandCollection() {
        locker.lock();
        try {
            return (LinkedList<MusicBand>) MUSIC_BAND_COLLECTION.clone();
        } finally {
            locker.unlock();
        }
    }

    public static String getDateCreated() {
        return DATE_CREATED.toString();
    }

    public static void clear() {
        locker.lock();
        try {
            MUSIC_BAND_COLLECTION.clear();
            ALL_ID.clear();
        } finally {
            locker.unlock();
        }
    }

    private static void checkDuplicateID(int id) throws IncorrectDataEntryInFileExceptions {
        if (ALL_ID.contains(id)) {
            throw new IncorrectDataEntryInFileExceptions("Такой id уже существует");
        }
    }

    public static void add(MusicBand musicBand) {
        locker.lock();
        try {
            MUSIC_BAND_COLLECTION.add(musicBand);
            ALL_ID.add(musicBand.getId());
        } finally {
            locker.unlock();
        }
    }

    public static void addFromServerFile(MusicBand musicBand) throws IncorrectDataEntryInFileExceptions {
        locker.lock();
        try {
            checkDuplicateID(musicBand.getId());
            ALL_ID.add(musicBand.getId());
            MUSIC_BAND_COLLECTION.add(musicBand);
        } finally {
            locker.unlock();
        }
    }

    public static void show(Printer printer) {
        locker.lock();
        try {
            Collections.sort(MUSIC_BAND_COLLECTION);
            if (MUSIC_BAND_COLLECTION.isEmpty()) {
                printer.println("В коллекции пока ничего нет");
            } else {
                MUSIC_BAND_COLLECTION.forEach(
                        (musicBand) -> printer.println(musicBand.toString())
                );
            }
        } finally {
            locker.unlock();
        }
    }

    public static boolean checkExistsID(int id) {
        return ALL_ID.contains(id);
    }

    public static void updateItem(MusicBand musicBandNew) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions {
        locker.lock();
        try {
            VALIDATION_MUSIC_BAND.checkMusicBand(musicBandNew);
            MusicBand musicBandFromCollection = getAndDeleteMusicBandById(musicBandNew.getId());
            MUSIC_BAND_COLLECTION.add(mergeMusicBands(musicBandFromCollection, musicBandNew));
        } finally {
            locker.unlock();
        }
    }

    private static MusicBand mergeMusicBands(MusicBand musicBandFromCollection, MusicBand musicBandNew) {
        locker.lock();
        try {
            musicBandNew.setId(musicBandFromCollection.getId());
            musicBandNew.setCreationDate(musicBandFromCollection.getCreationDate());
            return musicBandNew;
        } finally {
            locker.unlock();
        }
    }

    private static MusicBand getAndDeleteMusicBandById(int id) {
        locker.lock();
        try {
            for (MusicBand elem : MUSIC_BAND_COLLECTION) {
                if (elem.getId() == id) {
                    MUSIC_BAND_COLLECTION.remove(elem);
                    return elem;
                }
            }
            return null;
        } finally {
            locker.unlock();
        }
    }

    public static void delete(int id) {
        locker.lock();
        try {
            getAndDeleteMusicBandById(id);
            ALL_ID.remove(id);
        } finally {
            locker.unlock();
        }
    }

    public static void delete(ArrayList<Integer> arrIds) {
        locker.lock();
        try {
            MUSIC_BAND_COLLECTION.removeIf(musicBand -> arrIds.contains(musicBand.getId()));
            arrIds.forEach(ALL_ID::remove);
        } finally {
            locker.unlock();
        }
    }

    public static void getUniqueNumberOfParticipants(Printer printer) {
        locker.lock();
        try {
            Set<Long> uniqueParticipants = MUSIC_BAND_COLLECTION.stream()
                    .filter(
                            musicBand -> musicBand.getSafeNumberOfParticipants().isPresent()
                    ).map(
                            musicBand -> musicBand.getSafeNumberOfParticipants().get()
                    ).collect(Collectors.toSet());
            printer.println(uniqueParticipants.toString());
        } finally {
            locker.unlock();
        }
    }

    public static long countNumberOfParticipants(long number) {
        locker.lock();
        try {
            return MUSIC_BAND_COLLECTION.stream()
                    .filter(
                            musicBand -> musicBand.getSafeNumberOfParticipants().isPresent()
                                    && musicBand.getSafeNumberOfParticipants().get() < number
                    ).count();
        } finally {
            locker.unlock();
        }
    }
}
