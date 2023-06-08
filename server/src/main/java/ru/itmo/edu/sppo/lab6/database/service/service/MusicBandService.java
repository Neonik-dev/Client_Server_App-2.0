package ru.itmo.edu.sppo.lab6.database.service.service;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MusicBandService {
    MusicBand add(MusicBand musicBand) throws SQLException;

    ArrayList<MusicBand> getAll() throws SQLException;
}
