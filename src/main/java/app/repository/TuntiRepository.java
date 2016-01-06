package app.repository;

import app.domain.Tunti;
import app.domain.Projekti;
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

@Repository
public class TuntiRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    public Tunti findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Tunti Where id = ?", tuntiMapper, id);
    }
    
    public List<Tunti> findAll() {
        return jdbc.query("SELECT * FROM Tunti;", tuntiMapper);
    }
    
    public List<Tunti> findAllByKayttajaAndProjekti(String kayttaja, int projektiId) {
        return jdbc.query("SELECT * FROM Tunti WHERE projekti_id=? AND kayttaja=(SELECT id FROM Kayttaja WHERE email=?);",
            tuntiMapper, projektiId, kayttaja);
    }

    public void save(Tunti tunti) {
        LocalDateTime ldt = LocalDateTime.now();
        String alkuaika = ldt.toString();
        
        String sql = "INSERT INTO TUNTI(kayttaja, projekti_id, alkuaika) VALUES(?, ?, ?)";
        
        jdbc.update(sql, tunti.getKayttajaId(), tunti.getProjektiId(), alkuaika);
    }
    
    public void loppu(Tunti tunti) {
        LocalDateTime ldt = LocalDateTime.now();
        String loppuaika = ldt.toString();
        
        String sql = "UPDATE Tunti SET loppuaika=?, kuvaus=? WHERE id=?";
        
        jdbc.update(sql, loppuaika, tunti.getKuvaus(), tunti.getId());
    }

    private static final RowMapper<Tunti> tuntiMapper = new RowMapper<Tunti>() {
        public Tunti mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tunti tunti = new Tunti();
         tunti.setId(rs.getInt("id"));
            tunti.setKayttajaId(rs.getInt("kayttaja"));
            tunti.setProjektiId(rs.getInt("projekti_id"));
            tunti.setKuvaus(rs.getString("kuvaus"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            tunti.setAlkuaika(LocalDateTime.parse(rs.getString("alkuaika"), formatter));
            tunti.setLoppuaika(LocalDateTime.parse(rs.getString("loppuaika"), formatter));
            
            return tunti;
        }
    };
    
}