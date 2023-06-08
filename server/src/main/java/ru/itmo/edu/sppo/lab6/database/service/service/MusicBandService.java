package ru.itmo.edu.sppo.lab6.database.service.service;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MusicBandService {
    MusicBand add(MusicBand musicBand, int userId) throws SQLException;

    ArrayList<MusicBand> getAll() throws SQLException;

    int deleteByIdAndUserId(int musicBandId, int userId) throws SQLException;

    MusicBand deleteAndReturnHeadByUserId(int userId) throws SQLException, IncorrectDataEntryExceptions;
}
