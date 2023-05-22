package ru.itmo.edu.sppo.lab6.dto;

import lombok.Builder;
import lombok.Getter;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;

import java.io.Serializable;

@Builder
@Getter
public class ClientRequest implements Serializable {
    private String commandName;
    private String[] argument;
    private MusicBand musicBand;
}
