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
import app.domain.Tunti;
import app.ProjektinKesto;

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
    
    // @Autowired
    // private TuntiRepository tunnit;

    private Logger log = LoggerFactory.getLogger(getClass());
    private ProjektinKesto projektinKesto;
    
    public ProjektiRepository() {
        this.projektinKesto = new ProjektinKesto();
    }
    
    public Projekti findOne(int id) {
        return jdbc.queryForObject("SELECT * FROM Projekti Where id = ?", projektiMapper, id);
    }
    
    public List<Projekti> findAll() {
        return jdbc.query("SELECT * FROM projekti ORDER BY nimi", projektiMapper);
    }
    
    public List<Projekti> findAllByUser(String kayttaja) {
        return jdbc.query("SELECT * FROM Projekti WHERE omistaja_kayttaja=(SELECT id FROM Kayttaja WHERE email=?) ORDER BY nimi", projektiMapper, kayttaja);
    }
    
    public void save(Projekti projekti, String kayttaja) {
        String sql = "INSERT INTO projekti(nimi, kuvaus, omistaja_kayttaja, kesto) VALUES (?, ?, (SELECT id FROM Kayttaja WHERE email=?), 'PT0H0M')";
        
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
    
    public void lisaaTunti(int projektiId) {
        String sql = "UPDATE Projekti SET kesto= CAST(? AS interval) WHERE id=?";

        jdbc.update(sql, projektinKesto.kokonaiskesto(projektiId), projektiId);
        System.out.println("tallennettu tietokantaan projekti-id: " + projektiId);
    }
    
    public void poistaTunti(int projektiId) {
        // String sql = "UPDATE Projekti SET kesto= CAST(? AS interval) WHERE id=?";
        //
        // jdbc.update(sql, projektinKesto.kokonaiskesto(projektiId), projektiId);
    }
    
    public void paivitaKesto(int projektiId, Duration kesto) {
        String sql = "UPDATE Projekti SET kesto= CAST(? AS interval) WHERE id=?";
        
        jdbc.update(sql, kesto.toString(), projektiId);
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
    
    // private Duration kokonaiskesto(String kayttaja, int projektiId) {
    //     List<Tunti> kaikkiTunnit = tunnit.findAllByKayttajaAndProjekti(kayttaja, projektiId);
    //     Duration kesto = kaikkiTunnit.get(0).getDuration();
    //
    //     for (int i = 1; i < kaikkiTunnit.size(); i++) {
    //         kesto.plus(kaikkiTunnit.get(i).getDuration());
    //     }
    //
    //     return kesto;
    // }
    
}
