package ru.itmo.edu.sppo.lab6.dto;

import java.io.Serializable;

public record ClientResponse(String answer, String exception) implements Serializable {
    public ClientResponse(String answer) {
        this(answer, null);
    }
}
