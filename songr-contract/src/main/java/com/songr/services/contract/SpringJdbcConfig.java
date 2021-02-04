package com.songr.services.contract;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class SpringJdbcConfig {
    public static JdbcTemplate myJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://studmysql01.fhict.local/dbi333207");
        dataSource.setUsername("dbi333207");
        dataSource.setPassword("sJN5U!Tc");

        return new JdbcTemplate(dataSource);
    }
}
