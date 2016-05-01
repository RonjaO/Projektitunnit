package app.domain;

import java.time.LocalDateTime;
import java.time.Duration;
import org.hibernate.validator.constraints.Length;

public class Tunti {
    
    private int id;
    
    @Length(max = 200, message = "Kommentin pituus saa olla enintään 200 merkkiä")
    private String kuvaus;

    private LocalDateTime alkuaika;
    private LocalDateTime loppuaika;
    
    private int pp;
    private int kk;
    private int vvvv;
    private int alkuHh;
    private int alkuMm;
    private int loppuHh;
    private int loppuMm;

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
    
    public LocalDateTime getAlkuaika() {
        return this.alkuaika;
    }
    
    public void setAlkuaika(LocalDateTime alkuaika) {
        this.alkuaika = alkuaika;
        this.pp = this.alkuaika.getDayOfMonth();
        this.kk = this.alkuaika.getMonthValue();
        this.vvvv = this.alkuaika.getYear();
        this.alkuHh = this.alkuaika.getHour();
        this.alkuMm = this.alkuaika.getMinute();
    }
    
    public LocalDateTime getLoppuaika() {
        return this.loppuaika;
    }
    
    public void setLoppuaika(LocalDateTime loppuaika) {
        this.loppuaika = loppuaika;
        
        this.loppuHh = this.loppuaika.getHour();
        this.loppuMm = this.loppuaika.getMinute();
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
        this.projektiId = id;
    }
    
    public String getPaivays() {
        return this.alkuaika.getDayOfMonth() + "." + this.alkuaika.getMonthValue() + "." + this.alkuaika.getYear();
    }
    
    public int getPP() {
        return this.pp;
    }
    
    public int getKK() {
        return this.kk;
    }
    
    public int getVVVV() {
        return this.vvvv;
    }
    
    public void setPP(int pp) {
        this.pp = pp;
    }
    
    public void setKK(int kk) {
        this.kk = kk;
    }
    
    public void setVVVV(int vvvv) {
        this.vvvv = vvvv;
    }
    
    public int getAlkuHH() {
        return this.alkuHh;
    }
    
    public void setAlkuHH(int hh) {
        this.alkuHh = hh;
    }
    
    
    public int getAlkuMM() {
        return this.alkuMm;
    }
    
    public void setAlkuMM(int mm) {
        this.alkuMm = mm;
    }
    
    public int getLoppuHH() {
        return this.loppuHh;
    }
    
    public void setLoppuHH(int hh) {
        this.loppuHh = hh;
    }
    
    public int getLoppuMM() {
        return this.loppuMm;
    }
    
    public void setLoppuMM(int mm) {
        this.loppuMm = mm;
    }
    
    public String getKesto() {
        if (this.loppuaika == null) {
            return "kesken...";
        }
        
        Duration kesto = getDuration();
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
        return h + " h " + min + " min";
    }
    
    public Duration getDuration() {
        Duration kesto = Duration.between(this.alkuaika, this.loppuaika);
        
        return kesto;
    }

}
