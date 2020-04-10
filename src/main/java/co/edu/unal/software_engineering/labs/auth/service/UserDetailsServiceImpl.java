package co.edu.unal.software_engineering.labs.auth.service;

import co.edu.unal.software_engineering.labs.auth.model.UserDetailsImpl;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private UserService userService;

    public UserDetailsServiceImpl( UserService userService ){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException{
        User user = userService.findByUsername( username );
        if( user == null ){
            throw new UsernameNotFoundException( "" );
        }
        return new UserDetailsImpl( user );
    }

}
