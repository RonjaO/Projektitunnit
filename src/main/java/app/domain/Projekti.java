package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



public class Projekti {
    
    private int id;
    private int omistaja_kayttaja;
    private int omistaja_ryhma;
    
    @NotBlank
    @Length(min = 1, max =50)
    private String nimi;
    @Length(min = 5, max = 200)
    private String kuvaus;
    
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
    
}