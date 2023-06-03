package ru.itmo.edu.sppo.lab6.dto.collectionItem;

import java.io.Serializable;

public record Coordinates(
        double x, //Поле не может быть null
        long y //Поле не может быть null
) implements Serializable {
}
