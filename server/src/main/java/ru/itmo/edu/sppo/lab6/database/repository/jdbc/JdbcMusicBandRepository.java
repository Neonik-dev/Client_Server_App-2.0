package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.MusicBandRepository;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.ConvertorMusicBand;

import java.sql.*;
import java.util.ArrayList;

public class JdbcMusicBandRepository implements MusicBandRepository {
    private static final String INSERT_QUERY = """
            INSERT INTO music_band(name, coordinate_x, coordinate_y, number_of_participants, description,
                establishment_date, genre_id, studio_address, user_id)
            VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM genre WHERE name=?), ?, ?)
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
    private static final String DELETE_BY_ID_AND_USER_ID_QUERY = "DELETE FROM music_band WHERE id=? AND user_id=?";
    private static final String SELECT_HEAD_BY_USER_ID_QUERY = """
            SELECT music_band.id, music_band.name, coordinate_x, coordinate_y, creation_date, number_of_participants,
                description, establishment_date, genre.name as genre, studio_address
            FROM music_band
                JOIN genre ON genre_id = genre.id
            WHERE music_band.id=(
                SELECT id FROM music_band
                WHERE user_id=?
                ORDER BY id ASC LIMIT 1
            )""";

    @Override
    public int add(Connection conn, MusicBand musicBand, int userId) throws SQLException {
        try (var statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ConvertorMusicBand.convertToDB(statement, musicBand, userId);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
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

            return ConvertorMusicBand.convertFromDB(result);
        }
    }

    @Override
    public ArrayList<MusicBand> getAll(Connection conn) throws SQLException {
        ArrayList<MusicBand> allMusicBand = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL_QUERY);
            while (result.next()) {
                allMusicBand.add(ConvertorMusicBand.convertFromDB(result));
            }
        }
        return allMusicBand;
    }

    @Override
    public int deleteByIdAndUserId(Connection conn, int musicBandId, int userId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_BY_ID_AND_USER_ID_QUERY)) {
            statement.setInt(1, musicBandId);
            statement.setInt(2, userId);
            return statement.executeUpdate();
        }
    }

    @Override
    public MusicBand getHeadMusicBandByUserId(Connection conn, int userId) throws SQLException,
            IncorrectDataEntryExceptions {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_HEAD_BY_USER_ID_QUERY)) {
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return ConvertorMusicBand.convertFromDB(result);
            }
            throw new IncorrectDataEntryExceptions("Вы еще не создали ни одну запись, поэтому нечего удалять");
        }
    }
}
