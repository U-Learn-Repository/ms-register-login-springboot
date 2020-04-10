package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService( UserRepository userRepository ){
        this.userRepository = userRepository;
    }


    public User findByUsername( String username ){
        return userRepository.findByUsername( username );
    }

    public void save( User user ){
        userRepository.save( user );
    }

    public boolean isRightUser( RegisterUserPOJO user ){
        boolean correctness = user.getNames( ) != null && user.getPassword( ) != null && user.getUsername( ) != null &&
                user.getSurnames( ) != null;
        if( correctness ){
            correctness = !user.getNames( ).trim( ).isEmpty( )
                    && !user.getPassword( ).trim( ).isEmpty( )
                    && !user.getUsername( ).trim( ).isEmpty( )
                    && !user.getSurnames( ).trim( ).isEmpty( );
        }
        return correctness;
    }

}