package minimal.reproducible.example.demo;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Configuration
public class EmbeddedPostgresConfiguration {

    // Sätt nedanstående till 0 för att låta systemet själv välja ledig port
    public static final int PG_DB_PORT = 0;

    @Primary
    @Bean("EmbeddedVarderingPGDatasource")
    @FlywayDataSource
    DataSource embeddedPostgreSqlDataSource() throws IOException {
        var pg = embeddedPostgres();
        return pg.getPostgresDatabase();
    }

    @Bean(destroyMethod = "close")
    EmbeddedPostgres embeddedPostgres() throws IOException {
        var pgb = EmbeddedPostgres.builder();

        if (PG_DB_PORT != 0) {
            pgb.setPort(PG_DB_PORT);
        }

        if (SystemUtils.OS_NAME.toLowerCase().contains("windows")) {
            // Flytta temp-dir till en katalog där vi kan styra antivirusprogrammet bättre
            pgb.setDataDirectory("c:\\dev\\embedded_pg_temp\\demo\\" + Calendar.getInstance().getTimeInMillis());
            pgb.setOverrideWorkingDirectory(new File("c:\\dev\\embedded_pg_temp\\binaries"));
        }

        return pgb.start();
    }

}
