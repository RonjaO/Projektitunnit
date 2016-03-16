package app;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(jdbc.getDataSource())
            .usersByUsernameQuery("SELECT email, password, true FROM Kayttaja WHERE email=?")
            .authoritiesByUsernameQuery("SELECT email,'FOO' FROM kayttaja WHERE email=?")
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                // .antMatchers("/**").permitAll()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated();


        http.formLogin()
            // parametri "true" on tärkeä, ilman sitä näytetään css-tiedosto
            .defaultSuccessUrl("/projektit", true)
            .loginPage("/login")
            .usernameParameter("email").passwordParameter("password")
            .permitAll();
            
            http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignoroidaan public/CSS-kansion osalta Spring security, jotta tyylit toimii
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/javascript/**");
    }

    // @Configuration
    // protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
    //     @Override
    //     public void init(AuthenticationManagerBuilder auth) throws Exception {
    //         // käyttäjällä jack, jonka salasana on bauer, on rooli USER
    //         auth.inMemoryAuthentication()
    //                 .withUser("jack").password("bauer").roles("USER");
    //     }
    // }
    

}
