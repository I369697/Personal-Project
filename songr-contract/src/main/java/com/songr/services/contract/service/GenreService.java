package com.songr.services.contract.service;

import com.songr.services.contract.SpringJdbcConfig;
import com.songr.services.contract.assembler.GenreResourceAssembler;
import com.songr.services.contract.resource.GenreResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GenreService {

    @Autowired
    private GenreResourceAssembler genreResourceAssembler;
    @Autowired
    private TokenService tokenService;

    public String test(String id, String password) {
        return "genre: " + id;
    }

    public GenreResource getGenre(String token, String genre) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            final Object[] parameters = new Object[]{genre};
            final String sql = "SELECT g1.genre AS genre, (SELECT pg.genre FROM genre AS pg WHERE g1.p_id = pg.id) AS parent_genre FROM genre AS g1 WHERE g1.genre = ? LIMIT 1";

            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                result.put("name", rowSet.getString("genre"));
                result.put("parentGenre", rowSet.getString("parent_genre"));
            }

            return genreResourceAssembler.toResource(result);
        } else {
            return new GenreResource();
        }
    }

    public GenreResource setGenre(String token, String genre) {
        if (tokenService.checkToken(token)) {
            Map<String, Object> result = new LinkedHashMap<>();

            final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
            Object[] parameters = new Object[]{genre};
            String sql = "SELECT COUNT(*) AS count FROM genre WHERE genre = ?";
            final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

            while (rowSet.next()) {
                if (rowSet.getInt("count") > 0) {
                    result.put("name", "Genre already exists");
                    result.put("parentGenre", "");
                } else {
                    parameters = new Object[]{genre};
                    jdbcTemplate.update("INSERT INTO genre (id, genre, p_id) VALUES (null, ?, null)", parameters);

                    result.put("name", genre);
                    result.put("parentGenre", genre);
                }
            }

            return genreResourceAssembler.toResource(result);
        } else {
            return new GenreResource();
        }
    }
}
