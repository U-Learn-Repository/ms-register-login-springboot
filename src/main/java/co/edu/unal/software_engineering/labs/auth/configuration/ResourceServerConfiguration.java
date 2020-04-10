package co.edu.unal.software_engineering.labs.auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    private final static String[] publicResources = new String[]{ "/registro/**", "/roles" };
    private final static String[] userResources = new String[]{ "/usuario/**" };
    private final static String[] teacherResources = new String[]{ "/profesor/**" };
    private final static String[] studentResources = new String[]{ "/estudiante/**" };

    @Override
    public void configure( HttpSecurity httpSecurity ) throws Exception{
        httpSecurity
                .authorizeRequests( )
                .antMatchers( publicResources ).permitAll( )
                .antMatchers( userResources ).authenticated( )
                .antMatchers( teacherResources ).hasAuthority( "ROLE_PROFESOR" )
                .antMatchers( studentResources ).hasAuthority( "ROLE_ESTUDIANTE" );
    }

}
