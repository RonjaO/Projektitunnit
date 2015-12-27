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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjektiRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public Projekti findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Projekti Where id = ?", projektiMapper, id);
    }
    
    public List<Projekti> findAll() {
        return jdbc.queryForObject("SELECT * FROM projektit", projektiMapper);
    }
    
    private static final RowMapper<Projekti> projektiMapper = new RowMapper<Projekti>() {
        public Projekti mapRow(ResultSet rs, int rowNum) throws SQLException {
            Projekti projekti = new Projekti(rs.getString("nimi"));
            projekti.setId(rs.getInt("id"));
            projekti.setOmistaja_kayttaja(rs.getString("omistaja_kayttaja"));
            projekti.setOmistaja_ryhma(rs.getString("omistaja_ryhma"));
            projekti.setKuvaus(rs.getString("kuvaus"));
            
            return projekti;
        }
    };
    
}