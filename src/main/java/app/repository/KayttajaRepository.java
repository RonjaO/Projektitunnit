package app.repository;

import app.domain.Kayttaja;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class KayttajaRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public Kayttaja findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Kayttaja WHERE id = ?", kayttajaMapper, id);
    }
    
    public List<Kayttaja> findAll() {
        return jdbc.query("SELECT * FROM Kayttaja", kayttajaMapper);
    }
    
    public void save(Kayttaja kayttaja) {
        String sql = "INSERT INTO Kayttaja(email, nimi, password) VALUES (?, ?, ?)";
        
        jdbc.update(sql, kayttaja.getEmail(), kayttaja.getNimi(), kayttaja.getPassword());
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM Kayttaja WHERE id = ?";
        
        jdbc.update(sql, id);
    }
    
    public void update(Kayttaja kayttaja) {
        String sql = "UPDATE Kayttaja SET email=?, nimi=?, password=? WHERE id=?";
        
        jdbc.update(sql, kayttaja.getEmail(), kayttaja.getNimi(), kayttaja.getPassword());
    }
    
    
    private static final RowMapper<Kayttaja> kayttajaMapper = new RowMapper<Kayttaja>() {
        public Kayttaja mapRow(ResultSet rs, int rowNum) throws SQLException {
            Kayttaja kayttaja = new Kayttaja();
            kayttaja.setId(rs.getInt("id"));
            kayttaja.setEmail(rs.getString("email"));
            kayttaja.setNimi(rs.getString("nimi"));
            kayttaja.setPassword(rs.getString("password"));
            
            return kayttaja;
        }
    };
    
}