package ru.itmo.edu.sppo.lab6.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;

import java.io.Serializable;

@Builder
@Getter
@ToString
public class ClientRequest implements Serializable {
    private String commandName;
    private String[] argument;
    private MusicBand musicBand;
}
