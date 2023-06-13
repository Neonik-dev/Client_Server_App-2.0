package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.Coordinates;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicGenre;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.Studio;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConvertorMusicBand {
    private ConvertorMusicBand() {
    }

    public static void convertToDB(PreparedStatement statement, MusicBand musicBand, int userId)
            throws SQLException {
        statement.setString(1, musicBand.getName());
        statement.setDouble(2, musicBand.getCoordinates().x());
        statement.setLong(3, musicBand.getCoordinates().y());
        statement.setObject(4, musicBand.getNumberOfParticipants());
        statement.setString(5, musicBand.getDescription());
        if (musicBand.getSafeEstablishmentDate().isPresent()) {
            statement.setDate(6, new Date(musicBand.getEstablishmentDate().getTime()));
        } else {
            statement.setObject(6, null);
        }
        statement.setString(7, musicBand.getGenre().name());
        Optional<String> address = musicBand.getStudio().getAddress();
        if (address.isPresent()) {
            statement.setString(8, address.get());
        } else {
            statement.setString(8, null);
        }
        statement.setInt(9, userId);
    }

    public static MusicBand convertFromDB(ResultSet result) throws SQLException {
        MusicBand musicBand = new MusicBand();
        musicBand.setId(result.getInt("id"));
        musicBand.setName(result.getString("name"));
        musicBand.setCoordinates(
                new Coordinates(
                        result.getDouble("coordinate_x"),
                        result.getLong("coordinate_y")
                )
        );
        musicBand.setCreationDate(result.getDate("creation_date").toLocalDate());
        musicBand.setNumberOfParticipants(result.getLong("number_of_participants"));
        musicBand.setDescription(result.getString("description"));
        musicBand.setEstablishmentDate(result.getDate("establishment_date"));
        musicBand.setGenre(
                MusicGenre.valueOf(result.getString("genre"))
        );
        musicBand.setStudio(new Studio(result.getString("studio_address")));
        return musicBand;
    }
}
