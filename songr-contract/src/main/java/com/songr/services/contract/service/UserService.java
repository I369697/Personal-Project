package com.songr.services.contract.service;

import com.mysql.cj.jdbc.Blob;
import com.songr.services.contract.SpringJdbcConfig;
import com.songr.services.contract.assembler.*;
import com.songr.services.contract.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

    @Autowired
    private final UserResourceAssembler userResourceAssembler;
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private PreferenceResourceAssembler preferenceResourceAssembler;
    @Autowired
    private AlbumResourceAssembler albumResourceAssembler;
    @Autowired
    private ArtistResourceAssembler artistResourceAssembler;
    @Autowired
    private GenreResourceAssembler genreResourceAssembler;
    @Autowired
    private TrackResourceAssembler trackResourceAssembler;

    public UserService() {
        userResourceAssembler = new UserResourceAssembler();
        tokenService = new TokenService();
    }

    public String test(String userName, String password) {
        return "user: " + userName + ", " + password;
    }

    public UserResource login(String userName, String password) {
        String newToken = tokenService.createToken();

        final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
        final Object[] parameters = new Object[]{newToken, userName, password};

        final String sql = "{CALL login(?, ?, ?)}";
        jdbcTemplate.update(sql, parameters);

        return getUser(newToken, userName);
    }

    public UserResource logout(String token, String userName) {
        Map<String, Object> result = new LinkedHashMap<>();

        final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
        final Object[] parameters = new Object[]{userName};

        final String sql = "{CALL logout(?)}";
        jdbcTemplate.update(sql, parameters);

        return getUser(token, userName);
    }

    public UserResource getUser(String token, String userName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{userName};
            final String sql = "{CALL Get_User_Details(?)}";

            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                result.put("id", rowSet.getInt("id"));
                result.put("userName", rowSet.getString("username"));
                result.put("firstName", rowSet.getString("first_name"));
                result.put("middleName", rowSet.getString("middle_name"));
                result.put("lastName", rowSet.getString("last_name"));
                result.put("email", rowSet.getString("email"));
                result.put("password", rowSet.getString("password"));
                result.put("dateOfBirth", rowSet.getString("date_of_birth"));
                result.put("gender", rowSet.getString("gender"));
                result.put("profileImage", rowSet.getString("profile_image"));
                result.put("token", rowSet.getString("token"));
                result.put("spotify_refresh_token", rowSet.getString("spotify_refresh_token"));
            }

            return userResourceAssembler.toResource(result);
        } else {
            return new UserResource();
        }
    }

    public UserResource setUser(String userName, String email, String password) {

        String newToken = tokenService.createToken();

        final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
        final Object[] parameters = new Object[]{newToken, userName, email, password};

        final String sql = "{CALL Set_User(?, ?, ?, ?)}";
        jdbcTemplate.update(sql, parameters);

        return getUser(newToken, userName);
    }

    public UserResource editUser(String token, String userName, String firstName, String middleName, String lastName, String email, String dateOfBirth, String gender) {
        if (tokenService.checkToken(token)) {
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{userName, firstName, middleName, lastName, email, dateOfBirth, gender};
            final String sql = "{CALL Update_User_Details(?, ?, ?, ?, ?, ?, ?)}";

            jdbcTemplate.update(sql, parameters);

            return getUser(token, userName);
        } else {
            return new UserResource();
        }
    }

    public UserResource changePassword(String token, String userName, String password) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            return userResourceAssembler.toResource(result);
        } else {
            return new UserResource();
        }
    }

    public void connectSpotify(String token, String spotifyRefreshToken) {
        //TODO: store spotify token
        if (tokenService.checkToken(token)) {
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{token, spotifyRefreshToken};
            final String sql = "{CALL Set_Spotify_Token(?, ?)}";

            jdbcTemplate.update(sql, parameters);
        } else {
            new UserResource();
        }
    }

    public UserResource uploadThumbnail(String token, String userName, Blob thumbnail) {
        if (tokenService.checkToken(token)) {
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{userName, thumbnail};
            final String sql = "{CALL Set_Thumbnail(?, ?)}";

            jdbcTemplate.update(sql, parameters);
            return getUser(token, userName);
        } else {
            return new UserResource();
        }
    }

    public PreferenceResource getPreferences(String token, int userId) {
        if (tokenService.checkToken(token)) {
            final Map<String, Object> result = new LinkedHashMap<>();
            final List<Map<String, Object>> ablums = new LinkedList<>();
            final List<Map<String, Object>> genres = new LinkedList<>();
            final List<Map<String, Object>> tracks = new LinkedList<>();
            final List<Map<String, Object>> artists = new LinkedList<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();

            //Albums
            Object[] parameters = new Object[]{userId};
            String sql = "SELECT album.album_name AS album_name, artist.artist_name AS artist_name FROM preference_album, album, artist WHERE preference_album.album_id = album.id AND album.artist_id = artist.id AND preference_album.user_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                Map<String, Object> album = new LinkedHashMap<>();
                album.put("albumName", rowSet.getString("album_name"));
                album.put("artistName", rowSet.getString("artist_name"));
                ablums.add(album);
            }
            result.put("albums", ablums);

            //Genres
            parameters = new Object[]{userId};
            sql = "SELECT genre AS genre FROM preference_genre, genre WHERE preference_genre.genre_id = genre.id AND preference_genre.user_id = ?";
            rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                Map<String, Object> genre = new LinkedHashMap<>();
                result.put("name", rowSet.getString("genre"));
                result.put("parentGenre", rowSet.getString("genre"));
                genres.add(genre);
            }
            result.put("genres", genres);

            //Tracks
            parameters = new Object[]{userId};
            sql = "SELECT track.track_name AS track_name, genre.genre AS genre, album.album_name AS album_name, artist.artist_name AS artist_name, track_info.info AS info FROM preference_track, track_info, genre, album, artist, track WHERE preference_track.track_id = track.id AND track_info.genre_id = genre.id AND track_info.album_id = album.id AND track_info.artist_id = artist.id AND track_info.track_id = track.id AND preference_track.user_id = ?";
            rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                Map<String, Object> track = new LinkedHashMap<>();
                track.put("trackName", rowSet.getString("track_name"));
                track.put("genre", rowSet.getString("genre"));
                track.put("albumName", rowSet.getString("album_name"));
                track.put("artistName", rowSet.getString("artist_name"));
                track.put("trackInfo", rowSet.getString("info"));
                tracks.add(track);
            }
            result.put("tracks", tracks);

            //Artists
            parameters = new Object[]{userId};
            sql = "SELECT artist.artist_name AS artist_name, artist.artist_info AS artist_info FROM preference_artist, artist WHERE preference_artist.artist_id =  artist.id AND preference_artist.user_id = ? ";
            rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                Map<String, Object> artist = new LinkedHashMap<>();
                artist.put("artistName", rowSet.getString("artist_name"));
                artist.put("artistInfo", rowSet.getString("artist_info"));
                artists.add(artist);
            }
            result.put("artists", artists);

            return preferenceResourceAssembler.toResource(result);
        } else {
            return new PreferenceResource();
        }
    }

    public AlbumResource setAlbum(String token, int userId, String albumName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{albumName};
            final String sql = "SELECT album.id AS id, album.album_name AS album_name, artist.artist_name AS artist_name FROM album INNER JOIN artist ON album.artist_id = artist.id WHERE album.album_name = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                parameters = new Object[]{userId, rowSet.getInt("id")};
                jdbcTemplate.update("INSERT INTO preference_album (id, user_id, album_id) VALUES (null, ?, ?)", parameters);

                result.put("albumName", albumName);
                result.put("artistName", "");
            }
            return albumResourceAssembler.toResource(result);
        } else {
            return new AlbumResource();
        }
    }

    public ArtistResource setArtist(String token, int userId, String artistName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{artistName};
            String sql = "SELECT id FROM artist WHERE artist_name = ?";

            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                parameters = new Object[]{userId, rowSet.getInt("id")};
                jdbcTemplate.update("INSERT INTO preference_artist (id, user_id, artist_id) VALUES (null, ?, ?)", parameters);

                result.put("artistName", artistName);
                result.put("artistInfo", "");
            }
            return artistResourceAssembler.toResource(result);
        } else {
            return new ArtistResource();
        }
    }

    public GenreResource setGenre(String token, int userId, String genre) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{genre};
            String sql = "SELECT id FROM genre WHERE genre = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                parameters = new Object[]{userId, rowSet.getInt("id")};
                jdbcTemplate.queryForRowSet("INSERT INTO preference_genre (id, user_id, genre_id) VALUES (null, ?, ?)", parameters);

                result.put("name", genre);
                result.put("parentGenre", genre);
            }
            return genreResourceAssembler.toResource(result);
        } else {
            return new GenreResource();
        }
    }

    public TrackResource setTrack(String token, int userId, String trackName, String artistName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();

            Object[] parameters = new Object[]{trackName};
            String sql = "SELECT id FROM track WHERE track_name = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                parameters = new Object[]{userId, rowSet.getInt("id")};
                sql = "INSERT INTO preference_track (id, user_id, track_id) VALUES (NULL, ?, ?)";
                jdbcTemplate.update(sql, parameters);

                result.put("trackName", trackName);
                result.put("genre", "");
                result.put("albumName", artistName);
                result.put("artistName", "");
                result.put("trackInfo", "");
            }

            return trackResourceAssembler.toResource(result);
        } else {
            return new TrackResource();
        }
    }
}
