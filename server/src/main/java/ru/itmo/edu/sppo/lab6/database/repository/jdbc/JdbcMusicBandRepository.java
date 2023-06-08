package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.MusicBandRepository;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.Coordinates;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicGenre;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.Studio;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class JdbcMusicBandRepository implements MusicBandRepository {
    private static final String INSERT_QUERY = """
            INSERT INTO music_band(name, coordinate_x, coordinate_y, number_of_participants, description,
            establishment_date, genre_id, studio_address)
            VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM genre WHERE name=?), ?)
            """;
    private static final String SELECT_BY_ID_QUERY = """
            SELECT music_band.id, music_band.name, coordinate_x, coordinate_y, creation_date, number_of_participants,
            description, establishment_date, genre.name as genre, studio_address
            FROM music_band
                JOIN genre ON genre_id = genre.id
            WHERE music_band.id=?
            """;
    private static final String SELECT_ALL_QUERY = """
            SELECT music_band.id, music_band.name, coordinate_x, coordinate_y, creation_date, number_of_participants,
            description, establishment_date, genre.name as genre, studio_address
            FROM music_band
                JOIN genre ON genre_id = genre.id
            """;

    @Override
    public int add(Connection conn, MusicBand musicBand) throws SQLException {
        try (var statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            convertMusicBandToDB(statement, musicBand);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private void convertMusicBandToDB(PreparedStatement statement, MusicBand musicBand)
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
    }

    @Override
    public MusicBand getMusicBandById(Connection conn, int musicBandId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, musicBandId);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new SQLException("Нет ни одной MusicBand с таким id");
            }

            return convertResultSetToMusicBand(result);
        }
    }

    private MusicBand convertResultSetToMusicBand(ResultSet result) throws SQLException {
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

    @Override
    public ArrayList<MusicBand> getAll(Connection conn) throws SQLException {
        ArrayList<MusicBand> allMusicBand = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL_QUERY);
            while (result.next()) {
                allMusicBand.add(convertResultSetToMusicBand(result));
            }
        }
        return allMusicBand;
    }
}
