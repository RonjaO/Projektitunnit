package app.domain;

import java.util.Date;

public class Tunti {
    
    private int id;
    private String kuvaus;
    private Date alkuaika;
    private Date loppuaika;
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
    
    public Date getalkuaika() {
        return this.alkuaika;
    }
    
    public void setAlkuaika(Date alkuaika) {
        this.alkuaika = alkuaika;
    }
    
    public Date getLoppuaika() {
        return this.loppuaika;
    }
    
    public void setLoppuaika(Date loppuaika) {
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
}