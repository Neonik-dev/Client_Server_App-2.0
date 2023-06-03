package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.Coordinates;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicGenre;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.Studio;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectLongTypeExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;
import java.util.Date;

public class ValidationMusicBand {
    private static final String NUMBER_MUST_MORE_ZERO_TEXT = "Число должно быть больше 0";
    private final Set<String> commandsName;

    public ValidationMusicBand(Set<String> commandsName) {
        this.commandsName = commandsName;
    }

    public void checkMusicBand(MusicBand musicBand) throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        validateId(musicBand.getId());
        validateName(musicBand.getName());
        validateNumberOfParticipants(musicBand.getNumberOfParticipants());
        validateCoordinates(musicBand.getCoordinates());
        validateDescription(musicBand.getDescription());
        validateEstablishmentDate(musicBand.getEstablishmentDate());
        validateGenre(musicBand.getGenre().name());
        validateStudio(musicBand.getStudio().getAddress());
    }

    public int validateId(String rawId) throws IncorrectDataEntryExceptions, UnexpectedCommandExceptions {
        if (rawId == null || rawId.isEmpty()) {
            throw new IncorrectDataEntryExceptions("Поле id не может быть пустым");
        }
        checkCommand(rawId);

        String errorMessage;
        try {
            int id = Integer.parseInt(rawId);
            return validateId(id);
        } catch (NumberFormatException e) {
            errorMessage = "Необходимо ввести целое число!";
        }
        throw new IncorrectDataEntryExceptions(errorMessage);
    }

    public int validateId(int id) throws IncorrectDataEntryExceptions {
        if (id <= 0) {
            throw new IncorrectDataEntryExceptions(NUMBER_MUST_MORE_ZERO_TEXT);
        }
        return id;
    }

    public String validateName(String name) throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        if (name != null && !name.isEmpty()) {
            checkCommand(name);
            return name;
        }
        throw new IncorrectDataEntryExceptions("Название группы не может быть пустым!");
    }

    public Coordinates validateCoordinates(String[] coordinates) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions {
        checkCommand(coordinates[0]);
        if (coordinates.length != 2) {
            throw new IncorrectDataEntryExceptions("Необходимо ввести 2 числа - долготу и широту");
        }

        String errorMessage;
        try {
            return new Coordinates(
                    Double.parseDouble(coordinates[0]),
                    Long.parseLong(coordinates[1])
            );
        } catch (NumberFormatException | NullPointerException e) {
            errorMessage =
                    "Долгота должна быть вещественным числом (в качестве разделителя использовать '.'), "
                            + "а широта - целое число (-2^63 <= широта <= 2^63 -1).";
        }
        throw new IncorrectDataEntryExceptions(errorMessage);
    }

    public Coordinates validateCoordinates(Coordinates coordinates) {
        return coordinates;
    }

    public LocalDate validateCreationDate(String creationDate) throws IncorrectDataEntryExceptions {
        try {
            return LocalDate.parse(creationDate);
        } catch (DateTimeParseException e) {
            throw new IncorrectDataEntryExceptions(e.getMessage());
        }
    }

    public Long validateNumberOfParticipants(String number)
            throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        if (number == null || number.isEmpty()) {
            return null;
        }
        checkCommand(number);

        try {
            return validateNumberOfParticipants(CheckLongType.checkLong(number));
        } catch (IncorrectLongTypeExceptions e) {
            throw new IncorrectDataEntryExceptions(e.getMessage());
        }
    }

    public Long validateNumberOfParticipants(Long number)
            throws IncorrectDataEntryExceptions {
        if (number == null || number > 0) {
            return number;
        } else {
            throw new IncorrectDataEntryExceptions(NUMBER_MUST_MORE_ZERO_TEXT);
        }
    }

    public String validateDescription(String description)
            throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        if (description != null && !description.isEmpty()) {
            checkCommand(description);
            return description;
        }
        throw new IncorrectDataEntryExceptions("Описание группы не может быть пустым!");
    }

    public Date validateEstablishmentDate(String rawDate)
            throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        if (rawDate == null || rawDate.isEmpty()) {
            return null;
        }
        checkCommand(rawDate);

        String errorMessage;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        try {
            return validateEstablishmentDate(dateFormat.parse(rawDate));
        } catch (ParseException e) {
            errorMessage = "К сожалению, дата в неправильном формате";
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        throw new IncorrectDataEntryExceptions(errorMessage);
    }

    public Date validateEstablishmentDate(Date establishmentDate) throws IncorrectDataEntryExceptions {
        if (establishmentDate.before(new Date())) {
            return establishmentDate;
        }
        throw new IncorrectDataEntryExceptions("Укажите дату, меньше текущей");
    }

    public MusicGenre validateGenre(String genre) throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions {
        String errorMessage;
        if (genre == null || genre.isEmpty()) {
            errorMessage = "Поле genre не может быть пустым, необходимо выбрать один вариант из списка.";
        } else {
            checkCommand(genre);
            try {
                return MusicGenre.valueOf(genre.toUpperCase());
            } catch (IllegalArgumentException e) {
                errorMessage = "Необходимо выбрать один из перечисленных вариантов!";
            }
        }
        throw new IncorrectDataEntryExceptions(errorMessage);
    }

    public Studio validateStudio(String address) throws UnexpectedCommandExceptions {
        if (address == null || address.isEmpty()) {
            return new Studio(null);
        }

        checkCommand(address);
        return new Studio(address);
    }

    public void validateStudio(Optional<String> address) throws UnexpectedCommandExceptions {
        if (address.isPresent()) {
            validateStudio(address.get());
        }
    }

    public void checkCommand(String line) throws UnexpectedCommandExceptions {
        if (commandsName.contains(line.split(" ")[0])) {
            throw new UnexpectedCommandExceptions("Использовалось зарезервированное слово");
        }
    }
}
