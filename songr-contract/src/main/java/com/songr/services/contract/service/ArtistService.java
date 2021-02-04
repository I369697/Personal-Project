package com.songr.services.contract.service;

import com.songr.services.contract.SpringJdbcConfig;
import com.songr.services.contract.assembler.ArtistResourceAssembler;
import com.songr.services.contract.resource.ArtistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ArtistService {

    @Autowired
    private ArtistResourceAssembler artistResourceAssembler;
    @Autowired
    private TokenService tokenService;

    public String test(String id, String password) {
        return "artist: " + id + ", " + password;
    }

    public ArtistResource getArtist(String token, String artistName) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();
            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{artistName};
            final String sql = "SELECT artist_name, artist_info FROM artist WHERE artist_name = ? LIMIT 1";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);
            while (rowSet.next()) {
                result.put("artistName", rowSet.getString("artist_name"));
                result.put("artistInfo", rowSet.getString("artist_info"));
            }

            return artistResourceAssembler.toResource(result);
        } else {
            return new ArtistResource();
        }
    }

    public ArtistResource setArtist(String token, String artistName, String artistInfo) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{artistName};
            final String sql = "SELECT COUNT(*) AS count FROM artist WHERE artist_name = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                if (rowSet.getInt("count") > 0) {
                    result.put("artistName", "Artist exists");
                    result.put("artistInfo", "");
                } else {
                    parameters = new Object[]{artistName, ""};
                    jdbcTemplate.update("INSERT INTO artist (id, artist_name, artist_info) VALUES (null, ?, ?)", parameters);

                    result.put("artistName", artistName);
                    result.put("artistInfo", artistInfo);
                }
            }

            return artistResourceAssembler.toResource(result);
        } else {
            return new ArtistResource();
        }
    }
}
