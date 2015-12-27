package app.domain;

public class Projekti {
    
    private int id;
    private String omistaja_kayttaja;
    private String omistaja_ryhma;
    private String nimi;
    private String kuvaus;
    
    public Projekti(String nimi) {
        this.nimi = nimi;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getOmistaja_kayttaja() {
        return this.omistaja_kayttaja;
    }
    
    public void setOmistaja_kayttaja(String omistaja) {
        this.omistaja_kayttaja = omistaja;
    }
    
    public String getOmistaja_ryhma() {
        return this.omistaja_ryhma;
    }
    
    public void setOmistaja_ryhma(String omistaja) {
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