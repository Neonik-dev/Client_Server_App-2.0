package ru.itmo.edu.sppo.lab6.dto.collectionitem;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Data
public class MusicBand implements Serializable, Comparable<MusicBand> {
    int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    String name; //Поле не может быть null, Строка не может быть пустой
    Coordinates coordinates; //Поле не может быть null
    LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    String description; //Поле не может быть null
    Date establishmentDate; //Поле может быть null
    MusicGenre genre; //Поле не может быть null
    Studio studio; //Поле не может быть null

    public Optional<Long> getNumberOfParticipants() {
        return Optional.of(numberOfParticipants);
    }

    @Override
    public int compareTo(MusicBand item) {
        return name.compareTo(item.name);
    }
}
