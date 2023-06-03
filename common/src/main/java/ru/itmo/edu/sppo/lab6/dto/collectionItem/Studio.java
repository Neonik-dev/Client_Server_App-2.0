package ru.itmo.edu.sppo.lab6.dto.collectionItem;

import lombok.ToString;

import java.io.Serializable;
import java.util.Optional;

@ToString
public class Studio implements Serializable {
    private final String address; //Поле может быть null

    public Studio(String address) {
        this.address = address;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }
}
