package app.repository;

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
import java.time.Duration;

@Repository
public class ProjektiRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @Autowired
    private KayttajaRepository kayttajaRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public Projekti findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Projekti Where id = ?", projektiMapper, id);
    }
    
    public List<Projekti> findAll() {
        return jdbc.query("SELECT * FROM projekti ORDER BY nimi", projektiMapper);
    }
    
    public List<Projekti> findAllByUser(String kayttaja) {        return jdbc.query("SELECT * FROM projekti WHERE omistaja_kayttaja=(SELECT id FROM Kayttaja WHERE email=?);", projektiMapper, kayttaja);
    }
    
    public void save(Projekti projekti, String kayttaja) {
        String sql = "INSERT INTO projekti(nimi, kuvaus, omistaja_kayttaja) VALUES (?, ?, (SELECT id FROM Kayttaja WHERE email=?))";
        
        jdbc.update(sql, projekti.getNimi(), projekti.getKuvaus(), kayttaja);
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM projekti WHERE id = ?";
        
        jdbc.update(sql, id);
    }
    
    public void update(Projekti projekti, int id) {
        String sql = "UPDATE Projekti SET nimi=?, kuvaus=? WHERE id=?";
        
        jdbc.update(sql, projekti.getNimi(), projekti.getKuvaus(), id);
    }
    
    public void lisaaTunti(Projekti projekti, Duration tunti) {
        String sql = "UPDATE Projekti SET kesto=kesto+? WHERE id=?";
        
        jdbc.update(sql, tunti.toString(), projekti.getId());
    }
    
    
    private static final RowMapper<Projekti> projektiMapper = new RowMapper<Projekti>() {
        public Projekti mapRow(ResultSet rs, int rowNum) throws SQLException {
            Projekti projekti = new Projekti(rs.getString("nimi"), rs.getString("kuvaus"));
            projekti.setId(rs.getInt("id"));
            projekti.setOmistaja_kayttaja(rs.getInt("omistaja_kayttaja"));
            projekti.setOmistaja_ryhma(rs.getInt("omistaja_ryhma"));
projekti.setKesto(rs.getString("kesto"));

            return projekti;
        }
    };
    
}