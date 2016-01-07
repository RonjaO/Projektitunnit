package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.time.Duration;

public class Projekti {
    
    private int id;
    private int omistaja_kayttaja;
    private int omistaja_ryhma;
    @NotBlank(message="Projektin nimi ei saa olla tyhjä")
    @Length(min = 1, max =50, message="Nimen pituus saa olla korkeitaan 50 merkkiä")
    private String nimi;

    @Length(min = 5, max = 200, message="Kuvaus voi olla korkeintaan 200 merkkiä pitkä")
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
        return this.kesto;
    }
    
    public void setKesto(String kesto) {
        this.kesto = kesto;
    }
    
    public String getTunnit() {
        if (this.kesto.equals("")) {
            return " – ";
        }
        String[] kaikkiTunnit = this.kesto.split(":");
        
        return kaikkiTunnit[0] + "." + kaikkiTunnit[1];
    }
    
}