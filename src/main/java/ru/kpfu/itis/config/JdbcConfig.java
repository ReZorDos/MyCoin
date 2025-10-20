package ru.kpfu.itis.config;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.util.PropertyReader;

import javax.sql.DataSource;

@UtilityClass
public class JdbcConfig {

    @Getter
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

    private DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(PropertyReader.getProperties("DB_URL"));
        dataSource.setUser(PropertyReader.getProperties("DB_USER"));
        dataSource.setPassword(PropertyReader.getProperties("DB_PASSWORD"));
        return dataSource;
    }
}
