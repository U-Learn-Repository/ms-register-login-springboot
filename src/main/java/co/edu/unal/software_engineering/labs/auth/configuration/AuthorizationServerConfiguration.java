package co.edu.unal.software_engineering.labs.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    private final static String TRUSTED_CLIENT = "soft-eng-ii";
    private final static String SECRET = "secret";
    private final static String[] AUTHORIZED_GRANT_TYPES = new String[]{ "client_credentials", "password" };
    private final static String[] SCOPES = new String[]{ "read", "write", "trust" };
    private final static String[] RESOURCE_IDS = new String[] { "oauth2-resource" };
    private final static int ACCESS_TOKEN_VALIDITY_SECONDS = 1600;


    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    public AuthorizationServerConfiguration( AuthenticationManager authenticationManager,
                                             PasswordEncoder passwordEncoder ){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure( AuthorizationServerSecurityConfigurer security ){
        security
                .checkTokenAccess( "isAuthenticated()" );
    }

    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ){
        endpoints.authenticationManager( authenticationManager );
        endpoints.tokenStore( getTokenStore( ) );
    }

    @Override
    public void configure( ClientDetailsServiceConfigurer client ) throws Exception{
        client.inMemory( )
                .withClient( TRUSTED_CLIENT )
                .authorizedGrantTypes( AUTHORIZED_GRANT_TYPES ).scopes( SCOPES )
                .resourceIds( RESOURCE_IDS ).accessTokenValiditySeconds( ACCESS_TOKEN_VALIDITY_SECONDS )
                .secret( passwordEncoder.encode( SECRET ) );
    }

    @Bean
    public TokenStore getTokenStore( ){
        return new InMemoryTokenStore( );
    }
}
