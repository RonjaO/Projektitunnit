package app;

import java.net.URI;
import java.net.URISyntaxException;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;import org.springframework.context.annotation.Profile;


@Configuration
//@Profile("prod")
public class ProdProfile {

    @Bean
    //@Primary
    public DataSource dataSource() {
        return DataSourceBuilder
            .create()
            .url(System.getenv().get("DATABASE_URL"))
            .build();
    }
    
}
