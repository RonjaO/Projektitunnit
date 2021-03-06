package app.repository;

import app.domain.Tunti;
import app.domain.Projekti;
import app.repository.ProjektiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import app.domain.Kayttaja;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

@Repository
public class TuntiRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @Autowired
    private ProjektiRepository projektit;
    
    public Tunti findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Tunti Where id = ?", tuntiMapper, id);
    }
    
    public List<Tunti> findAll() {
        return jdbc.query("SELECT * FROM Tunti;", tuntiMapper);
    }
    
    public List<Tunti> findAllByKayttajaAndProjekti(String kayttaja, int projektiId) {
        return jdbc.query("SELECT * FROM Tunti WHERE projekti_id=? AND kayttaja=(SELECT id FROM Kayttaja WHERE email=?) ORDER BY alkuaika",
            tuntiMapper, projektiId, kayttaja);
    }
    
    public List<Tunti> findAllByProjekti(int projektiId) {
        System.out.println("Etsitään tunteja");
        return jdbc.query("SELECT * FROM Tunti WHERE projekti_id=? ",
            tuntiMapper, projektiId);
    }

    public void save(int projektiId, String kayttaja) {
        LocalDateTime ldt = LocalDateTime.now();
        String alkuaika = ldt.toString();
        
        String sql = "INSERT INTO Tunti(kayttaja, projekti_id, alkuaika) VALUES((SELECT id FROM Kayttaja WHERE email=?), ?, cast(? as timestamp))";
        
        jdbc.update(sql, kayttaja, projektiId, alkuaika);
    }
    
    public void update(Tunti tunti) {
        String alkuaika = uusiAlkuaika(tunti).toString();
        String loppuaika = uusiLoppuaika(tunti).toString();
        
        String sql = "UPDATE Tunti SET kuvaus=?, alkuaika=cast (? as timestamp), loppuaika=cast (? as timestamp) WHERE id=?";
        
        jdbc.update(sql, tunti.getKuvaus(), alkuaika, loppuaika, tunti.getId());
    }
    
    public void delete(int id) {
        Tunti tunti = findOne(id);
        
        String sql = "DELETE FROM Tunti WHERE id=?";
        jdbc.update(sql, id);
    }
    
    public void loppu(Tunti tunti) {
        Tunti loppuvaTunti = findOne(tunti.getId());
        LocalDateTime ldt = LocalDateTime.now();
        loppuvaTunti.setLoppuaika(ldt);
        String loppuaika = ldt.toString();
        
        String sql = "UPDATE Tunti SET loppuaika=cast (? as timestamp), kuvaus=? WHERE id=?";
        
        jdbc.update(sql, loppuaika, tunti.getKuvaus(), tunti.getId());
    } 

    public List<Tunti> kesken() {
        return jdbc.query("SELECT * FROM Tunti WHERE loppuaika IS NULL;", tuntiMapper);
    }
    
    private LocalDateTime uusiAlkuaika(Tunti tunti) {
        LocalDateTime alkuaika = LocalDateTime.of(tunti.getVVVV(), 
            tunti.getKK(), 
            tunti.getPP(), 
            tunti.getAlkuHH(), 
            tunti.getAlkuMM());

        tunti.setAlkuaika(alkuaika);

        return alkuaika;
    }
    
    private LocalDateTime uusiLoppuaika(Tunti tunti) {
        LocalDateTime loppuaika = LocalDateTime.of(tunti.getVVVV(),
            tunti.getKK(),
            tunti.getPP(),
            tunti.getLoppuHH(),
            tunti.getLoppuMM());
        
        tunti.setLoppuaika(loppuaika);
        
        return loppuaika;
    }

    private static final RowMapper<Tunti> tuntiMapper = new RowMapper<Tunti>() {
        public Tunti mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tunti tunti = new Tunti();
             tunti.setId(rs.getInt("id"));
            tunti.setKayttajaId(rs.getInt("kayttaja"));
            tunti.setProjektiId(rs.getInt("projekti_id"));
            tunti.setKuvaus(rs.getString("kuvaus"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            if (rs.getString("alkuaika").length() == 22 ) {
                 formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
            } else if (rs.getString("alkuaika").length() == 21) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            } else if (rs.getString("alkuaika").length() == 19) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }
            tunti.setAlkuaika(LocalDateTime.parse(rs.getString("alkuaika"), formatter));

            if (rs.getString("loppuaika") != null) {
                if (rs.getString("loppuaika").length() == 22) {
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                } else if (rs.getString("loppuaika").length() == 21) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            } else if (rs.getString("loppuaika").length() == 19) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            } else if (rs.getString("loppuaika").length() == 23) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            }
                tunti.setLoppuaika(LocalDateTime.parse(rs.getString("loppuaika"), formatter));
            }
            
            return tunti;
        }
    };
    
}
