package ru.itmo.edu.sppo.lab6.database.service.service;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;

import java.sql.SQLException;

public interface MusicBandService {
    MusicBand add(MusicBand musicBand) throws SQLException;
}
