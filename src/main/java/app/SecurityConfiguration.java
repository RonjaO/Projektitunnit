package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                // .antMatchers("/**").permitAll()
                .anyRequest().authenticated();


        http.formLogin()
            .loginPage("/login")
            .successHandler(successHandler())
            .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Disable Spring Security completely on public static resources.
        // This is the only way to avoid having "Cache-Control: must-revalidate" in the response, which
        // both reduces responsiveness, and breaks IE's font loading.
        web.ignoring().antMatchers("/public/**");     
    }
    
    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            // käyttäjällä jack, jonka salasana on bauer, on rooli USER
            auth.inMemoryAuthentication()
                    .withUser("jack").password("bauer").roles("USER");
        }
    }
    
    @Bean 
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler; 
    } 
}
