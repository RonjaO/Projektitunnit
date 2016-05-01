package app.profiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
@Profile(value = {"dev", "default"})
public class DevProfile {
    
    @Bean
    public Datasource datasourse() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("sql/create_tables.sql").addScript("sql/add_test_data.sql").build();
        
        return db;
    }

}
