package com.songr.services.contract.service;

import com.songr.services.contract.SpringJdbcConfig;
import com.songr.services.contract.assembler.AlbumResourceAssembler;
import com.songr.services.contract.resource.AlbumResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AlbumService {

    @Autowired
    private AlbumResourceAssembler albumResourceAssembler;
    @Autowired
    private TokenService tokenService;

    public String test(String id, String password) {
        return "album: " + id + ", " + password;
    }

    public AlbumResource getAlbum(String token, String albumName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{albumName};
            final String sql = "SELECT album.album_name AS album_name, artist.artist_name AS artist_name FROM album INNER JOIN artist ON album.artist_id = artist.id WHERE album.album_name = ? LIMIT 1";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                result.put("albumName", rowSet.getString("album_name"));
                result.put("artistName", rowSet.getString("artist_name"));
            }

            return albumResourceAssembler.toResource(result);
        } else {
            return new AlbumResource();
        }
    }

    public AlbumResource setAlbum(String token, String artistName, String albumName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{albumName};
            final String sql = "SELECT COUNT(*) AS count FROM album WHERE album_name = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                if (rowSet.getInt("count") > 0) {
                    result.put("albumName", "Already exists");
                    result.put("artistName", "");
                } else {
                    System.out.println("ble1");
                    parameters = new Object[]{artistName};
                    final String sql2 = "SELECT COUNT(*) AS count FROM artist WHERE artist_name = ?";
                    final SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql2, parameters);
                    while (rowSet2.next()) {
                        if (rowSet2.getInt("count") > 0) {
                            parameters = new Object[]{artistName};
                            final String sql3 = "SELECT id FROM artist WHERE artist_name = ?";
                            final SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql3, parameters);
                            while (rowSet3.next()) {
                                parameters = new Object[]{albumName, rowSet3.getInt("id")};
                                jdbcTemplate.update("INSERT INTO album (id, album_name, artist_id) VALUES (null, ?, ?)", parameters);

                                result.put("albumName", albumName);
                                result.put("artistName", artistName);
                            }
                        } else {
                            result.put("albumName", rowSet.getString("Artist doesn't exists"));
                            result.put("artistName", rowSet.getString(""));
                        }
                    }
                }
            }

            return albumResourceAssembler.toResource(result);
        } else {
            return new AlbumResource();
        }
    }
}