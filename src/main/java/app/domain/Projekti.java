package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.util.List;
import java.time.Duration;
import app.repository.TuntiRepository;

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
    
    public Projekti() {
        
    }
    
    public Projekti(String nimi, String kuvaus) {
        this.nimi = nimi;
        this.kuvaus = kuvaus;
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
        this.kesto = kokonaiskesto();
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
        List<Tunti> kaikkiTunnit = tunnit.findAllByProjekti(getId());
        System.out.println("Lasketaan kesto. Tunteja " + kaikkiTunnit.size());


        Duration aika = kaikkiTunnit.get(0).getDuration();
        
        if (kaikkiTunnit.size() > 1) {
            System.out.println("Lasketaan projektin kesto");
            for (int i = 1; i < kaikkiTunnit.size(); i++) {
                aika.plus(kaikkiTunnit.get(i).getDuration());
            }
        }
        
        return aika.toString();
    }
    
}
