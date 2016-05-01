package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import app.profiles.DevProfile;
import app.profiles.ProdProfile;


@SpringBootApplication
@Import({DevProfile.class, ProdProfile.class})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("Salasana: " + encoder.encode("matti123"));

        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void run() throws Exception {
        // jdbcTemplate.execute("DROP TABLE customers IF EXISTS");

        // log.info("jotain");
        // jdbcTemplate.query(
        //         "SELECT * FROM Projekti");
        //
    }

}
