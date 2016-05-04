package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.util.List;
import java.time.Duration;
import app.repository.TuntiRepository;
import app.ProjektinKesto;

public class Projekti {
    
    @Autowired
    private TuntiRepository tunnit;
    
    
    private int id;
    private int omistaja_kayttaja;
    private int omistaja_ryhma;
    @NotBlank(message="Projektin nimi ei saa olla tyhjä")
    @Length(min = 1, max =50, message="Nimen pituus saa olla korkeitaan 50 merkkiä")
    private String nimi;

    @Length(max = 200, message="Kuvaus voi olla korkeintaan 200 merkkiä pitkä")
    private String kuvaus;
    
    private String kesto;
    private ProjektinKesto projektinKesto;
    
    public Projekti() {
        this.projektinKesto = new ProjektinKesto();
    }
    
    public Projekti(String nimi, String kuvaus) {
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.projektinKesto = new ProjektinKesto();
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getOmistaja_kayttaja() {
        return this.omistaja_kayttaja;
    }
    
    public void setOmistaja_kayttaja(int omistaja) {
        this.omistaja_kayttaja = omistaja;
    }
    
    public int getOmistaja_ryhma() {
        return this.omistaja_ryhma;
    }
    
    public void setOmistaja_ryhma(int omistaja) {
        this.omistaja_ryhma = omistaja;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    
    public String getKesto() {
        // this.kesto = kokonaiskesto();
        return this.kesto;
    }
    
    public void setKesto(String kesto) {
        this.kesto = kesto;
    }
    
    public String getTunnit() {
        String[] kaikkiTunnit = kokonaiskesto().split(":");
        
        return kaikkiTunnit[0] + " h " + kaikkiTunnit[1] + " min";
    }
    
    private String kokonaiskesto() {
        System.out.println("Lasketaan. Tähänkö tämä kaatuu?");
        List<Tunti> kaikkiTunnit = tunnit.findAllByKayttajaAndProjekti("ronja.oja@gmail.com", 1);
        System.out.println("Lasketaan kesto. Tunteja " + kaikkiTunnit.size());

        if (kaikkiTunnit.size() == 0) {
            return "00:00";
        }

        Duration aika = Duration.ZERO;

        for (Tunti tunti : kaikkiTunnit) {
            if (tunti.getLoppuaika() != null || tunti.getAlkuaika() != null) {
                aika = aika.plus(tunti.getDuration());
            }
        }
        return aika.toString();
    }
    
}
