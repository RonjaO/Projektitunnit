package app.domain;

import java.time.LocalDateTime;

public class Tunti {
    
    private int id;
    private String kuvaus;

    private LocalDateTime alkuaika;
    private LocalDateTime loppuaika;
    private int kayttajaId;
    private int projektiId;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    
    public LocalDateTime getalkuaika() {
        return this.alkuaika;
    }
    
    public void setAlkuaika(LocalDateTime alkuaika) {
        this.alkuaika = alkuaika;
    }
    
    public LocalDateTime getLoppuaika() {
        return this.loppuaika;
    }
    
    public void setLoppuaika(LocalDateTime loppuaika) {
        this.loppuaika = loppuaika;
    }
    
    public int getKayttajaId() {
        return this.kayttajaId;
    }
    
    public void setKayttajaId(int id) {
        this.kayttajaId = id;
    }
    
    public int getProjektiId() {
        return this.projektiId;
    }
    
    public void setProjektiId(int id) {
        this.id = id;
    }
    
    public String getPaivays() {
        return this.alkuaika.getDayOfMonth() + "." + this.alkuaika.getMonth() + "." + this.alkuaika.getYear();
    }
    
    public String getKesto() {
        LocalDateTime kesto = this.loppuaika.minusHours(this.alkuaika.getHour());
        kesto = this.loppuaika.minusMinutes(this.alkuaika.getMinute());
        return kesto.getMinute() + ":" + kesto.getHour();
    }
}
