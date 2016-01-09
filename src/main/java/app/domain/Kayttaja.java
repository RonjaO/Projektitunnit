package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;

public class Kayttaja {
    
    private int id;

    // Sähköposti toimii käyttäjätunnuksena
    @NotBlank(message = "Sähköposti ei saa olla tyhjä")
    @Email(message = "Sähköpostin tulee olla muotoa esim@example.com")
    private String email;
    
    @NotBlank(message = "Nimi on pakollinen tieto")
    @Length(max = 50, message = "Nimen pituus saa olla korkeintaan 50 merkkiä")
    private String nimi;
    
    @NotBlank(message = "Salasana ei saa olla tyhjä")
    @Length(min=8, max = 50, message = "Salasanan pituus tulee olla 8-50 merkkiä")
    private String password;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String username) {
        this.email = username;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}