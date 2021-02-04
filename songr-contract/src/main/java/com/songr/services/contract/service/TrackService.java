package com.songr.services.contract.service;

import com.songr.services.contract.SpringJdbcConfig;
import com.songr.services.contract.assembler.TrackResourceAssembler;
import com.songr.services.contract.resource.TrackResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TrackService {

    @Autowired
    private TrackResourceAssembler trackResourceAssembler;
    @Autowired
    private TokenService tokenService;

    public String test(String id, String password) {
        return "track: " + id + ", " + password;
    }

    public TrackResource getTrack(String token, String trackName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{trackName};
            final String sql = "SELECT track.track_name AS track_name, genre.genre AS genre, album.album_name AS album_name, artist.artist_name AS artist_name, track_info.info AS info FROM track_info, genre, album, artist, track WHERE track_info.genre_id = genre.id AND track_info.album_id = album.id AND track_info.artist_id = artist.id AND track_info.track_id = track.id AND track.track_name = ? LIMIT 1";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                result.put("trackName", rowSet.getString("track_name"));
                result.put("genre", rowSet.getString("genre"));
                result.put("albumName", rowSet.getString("album_name"));
                result.put("artistName", rowSet.getString("artist_name"));
                result.put("trackInfo", rowSet.getString("info"));
            }

            return trackResourceAssembler.toResource(result);
        } else {
            return new TrackResource();
        }
    }

    public TrackResource setTrack(String token, String trackName, String genre, String albumName, String artistName, String trackInfo) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{trackName};
//            String sql                                                  = "SELECT COUNT(*) AS count FROM track_info, artist, track WHERE track_info.artist_id = artist.id AND track_info.track_id = track.id AND track.track_name = ? AND artist.artist_name = ? ";
            String sql = "SELECT COUNT(*) AS count FROM track WHERE track_name = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            System.out.println("ble");
            while (rowSet.next()) {
                System.out.println("ble1");
                if (rowSet.getInt("count") > 0) {
                    System.out.println("ble2");
                    result.put("trackName", "Track already exists");
                    result.put("genre", "");
                    result.put("albumName", "");
                    result.put("artistName", "");
                    result.put("trackInfo", "");
                } else {
                    System.out.println("ble3");
                    parameters = new Object[]{trackName};
                    sql = "INSERT INTO track (id, track_name) VALUES (NULL, ?)";
                    jdbcTemplate.update(sql, parameters);

                    int trackId = 0;
                    int albumId = 0;
                    int artistId = 0;
                    int genreId = 0;

                    parameters = new Object[]{trackName};
                    sql = "SELECT id FROM track WHERE track_name = ?";
                    final SqlRowSet rowSet1 = jdbcTemplate.queryForRowSet(sql, parameters);
                    while (rowSet1.next()) {
                        trackId = rowSet1.getInt("id");
                    }

                    parameters = new Object[]{albumName};
                    sql = "SELECT id FROM album WHERE album_name = ?";
                    final SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql, parameters);
                    while (rowSet2.next()) {
                        albumId = rowSet2.getInt("id");
                    }

                    parameters = new Object[]{artistName};
                    sql = "SELECT id FROM artist WHERE artist_name = ?";
                    final SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql, parameters);
                    while (rowSet3.next()) {
                        artistId = rowSet3.getInt("id");
                    }

                    parameters = new Object[]{genre};
                    sql = "SELECT id FROM genre WHERE genre = ?";
                    final SqlRowSet rowSet4 = jdbcTemplate.queryForRowSet(sql, parameters);
                    while (rowSet4.next()) {
                        genreId = rowSet4.getInt("id");
                    }

                    if (albumId > 0 && artistId > 0 && genreId > 0 && trackId > 0) {
                        System.out.println("ble4");
                        parameters = new Object[]{trackId, genreId, albumId, artistId, trackInfo};
                        sql = "INSERT INTO track_info (id, track_id, genre_id, album_id, artist_id, info) VALUES (NULL, ?, ?, ?, ?, ?)";
                        jdbcTemplate.update(sql, parameters);

                        result.put("trackName", trackName);
                        result.put("genre", genre);
                        result.put("albumName", albumName);
                        result.put("artistName", artistName);
                        result.put("trackInfo", trackInfo);
                    } else {
                        System.out.println("ble5");
                        result.put("trackName", trackName);
                        result.put("genre", (genreId > 0 ? genre : "Doesn't exist"));
                        result.put("albumName", (albumId > 0 ? albumName : "Doesn't exist"));
                        result.put("artistName", (artistId > 0 ? artistName : "Doesn't exist"));
                        result.put("trackInfo", "");
                    }
                }
            }

            return trackResourceAssembler.toResource(result);
        } else {
            return new TrackResource();
        }
    }
}
