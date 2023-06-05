package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.storage.Commands;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;

import java.util.HashMap;

public class CreateMusicBandFromFile {
    private final ValidationMusicBand musicBandValidation;

    public CreateMusicBandFromFile() {
        musicBandValidation = new ValidationMusicBand(new Commands().getAllCommand().keySet());
    }

    public MusicBand createFromFile(HashMap<String, String> data, HashMap<String, String> coordinates)
            throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        MusicBand musicBand = new MusicBand();

        musicBand.setId(musicBandValidation.validateId(data.get("id")));
        musicBand.setCreationDate(musicBandValidation.validateCreationDate(data.get("creationDate")));
        musicBand.setName(musicBandValidation.validateName(data.get("name")));
        musicBand.setNumberOfParticipants(
                musicBandValidation.validateNumberOfParticipants(data.get("numberOfParticipants"))
        );
        musicBand.setCoordinates(musicBandValidation.validateCoordinates(coordinates.values().toArray(new String[0])));
        musicBand.setDescription(musicBandValidation.validateDescription(data.get("description")));
        musicBand.setEstablishmentDate(musicBandValidation.validateEstablishmentDate(data.get("establishmentDate")));
        musicBand.setGenre(musicBandValidation.validateGenre(data.get("genre")));
        musicBand.setStudio(musicBandValidation.validateStudio(data.get("studio")));
        return musicBand;
    }
}
