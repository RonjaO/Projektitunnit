package app.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;

public class Kayttaja {
    
    private int id;

        // Sähköposti toimii käyttäjtunnuksena
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Length(max = 50)
    private String nimi;
    
    @NotBlank
    @Length(min=8, max = 50)
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