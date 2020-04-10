package co.edu.unal.software_engineering.labs.auth.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private UserDetailsService userDetailsService;

    public WebSecurityConfiguration( @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService ){
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure( AuthenticationManagerBuilder builder ) throws Exception{
        builder.userDetailsService( userDetailsService( ) ).passwordEncoder( passwordEncoder( ) );
    }

    @Override
    protected UserDetailsService userDetailsService( ){
        return userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean( ) throws Exception{
        return super.authenticationManagerBean( );
    }

    @Bean
    public PasswordEncoder passwordEncoder( ){
        return new BCryptPasswordEncoder( );
    }

}