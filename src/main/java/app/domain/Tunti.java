package app.domain;

import java.time.LocalDateTime;
import java.time.Duration;

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
        return this.alkuaika.getDayOfMonth() + "." + this.alkuaika.getMonthValue() + "." + this.alkuaika.getYear();
    }
    
    public String getKesto() {
        Duration kesto = Duration.between(this.alkuaika, this.loppuaika);
        long min = kesto.toMinutes();
        long h = 0;
        
        while (true) {
            if (min >= 60) {
                h++;
                min = min - 60;
            } else {
                break;
            }
        }
        return h + "." + min;
    }
}
