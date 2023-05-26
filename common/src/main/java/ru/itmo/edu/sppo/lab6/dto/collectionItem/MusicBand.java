package ru.itmo.edu.sppo.lab6.dto.collectionItem;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Data
public class MusicBand implements Serializable, Comparable<MusicBand> {
    private int id; //Значение поля должно быть больше 0, уникальным и генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private String description; //Поле не может быть null
    private Date establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Studio studio; //Поле не может быть null

    public Optional<Long> getSafeNumberOfParticipants() {
        return Optional.ofNullable(numberOfParticipants);
    }

    public Optional<Date> getSafeEstablishmentDate() {
        return Optional.ofNullable(establishmentDate);
    }

    @Override
    public int compareTo(MusicBand item) {
        return name.compareTo(item.name);
    }
}
