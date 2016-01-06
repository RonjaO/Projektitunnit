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
            
            String[] alkuaika = rs.getString("alkuaika").split(" ");
            String[] paivays = alkuaika[0].split("-");
            int vuosi = Integer.parseInt(paivays[0]);
            int kuukausi = Integer.parseInt(paivays[1]);
            int paiva = Integer.parseInt(paivays[2]);

            String[] klo = alkuaika[1].split(":");
            int tunnit = Integer.parseInt(klo[0]);
            int minuutit = Integer.parseInt(klo[1]);

            tunti.setAlkuaika(LocalDateTime.of(vuosi, kuukausi, paiva, tunnit, minuutit));
            
            if (rs.getString("loppuaika") != "") {
                String[] loppuaika = rs.getString("loppuaika").split(" ");
                String[] loppupaivays = loppuaika[0].split("-");
                vuosi = Integer.parseInt(loppupaivays[0]);
                kuukausi = Integer.parseInt(loppupaivays[1]);
                paiva = Integer.parseInt(loppupaivays[2]);

                String[] loppuklo = loppuaika[1].split(":");
                tunnit = Integer.parseInt(loppuklo[0]);
                minuutit = Integer.parseInt(loppuklo[1]);
                
                tunti.setLoppuaika(LocalDateTime.of(vuosi, kuukausi, paiva, tunnit, minuutit));
            }
            
            
            return tunti;
        }
    };
    
}