package com.songr.services.contract.service;

import com.songr.services.contract.SpringJdbcConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenService {
    public String test(String id, String password) {
        Map<String, Object> result = new LinkedHashMap<>();

        return "token: " + result.get("id") + ", " + result.get("password");
    }

    public String createToken() {
        //final JdbcTemplate jdbcTemplate                                                         = SpringJdbcConfig.myJdbcTemplate();
        final String newToken = UUID.randomUUID().toString();
        /*final Object[] parameters                                                               = new Object[] { new Integer(1), userName, newToken, new Integer(0)};
        final String sql                                                                        = "INSERT INTO user_token (id, user_id, token, valid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.queryForRowSet(sql, parameters);*/

        return newToken;
    }

    public boolean checkToken(String token) {
        final JdbcTemplate jdbcTemplate = SpringJdbcConfig.myJdbcTemplate();
        final Object[] parameters = new Object[]{token};
        final String sql = "SELECT user_id, token, valid FROM user_token WHERE token = ?";
        boolean tokenValid = false;
        final SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, parameters);

        while (rowSet.next()) {
            if (token.equals(rowSet.getString("token")) && rowSet.getBoolean("valid")) {
                tokenValid = true;
            }
        }

        return tokenValid;
    }
}