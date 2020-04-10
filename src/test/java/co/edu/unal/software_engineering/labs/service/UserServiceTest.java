package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith( SpringRunner.class )
@DataJpaTest
@AutoConfigureTestDatabase( replace = NONE )
public class UserServiceTest{

    @TestConfiguration
    static class UserServiceImpTestConfiguration{
        @Autowired
        private UserRepository userRepository;

        @Bean
        public UserService userService( ){
            return new UserService( userRepository );
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void crudTest( ){
        String names = "Test User";
        String surnames = "Labs SE-II UN";
        String username = "test";

        User createUser = new User( );
        createUser.setNames( names );
        createUser.setSurnames( surnames );
        createUser.setUsername( username );
        createUser.setPassword( username );
        userService.save( createUser );

        User readUser = userService.findByUsername( username );
        assertEquals( createUser, readUser );

        createUser.addRole( Role.getStudent( ) );
        userService.save( createUser );

        User updatedUser = userService.findByUsername( username );
        assertEquals( createUser.getRoles( ), updatedUser.getRoles( ) );
    }

    @Test
    public void isRightUserTest( ){
        RegisterUserPOJO user = new RegisterUserPOJO( );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "" );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( null );
        user.setPassword( "" );
        assertFalse( userService.isRightUser( user ) );

        user.setPassword( null );
        user.setSurnames( "" );
        assertFalse( userService.isRightUser( user ) );

        user.setSurnames( null );
        user.setUsername( "" );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "" );
        user.setSurnames( "" );
        user.setPassword( "" );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "   " );
        user.setSurnames( "   " );
        user.setPassword( "   " );
        user.setUsername( "   " );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "test" );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "   " );
        user.setSurnames( "Test" );
        assertFalse( userService.isRightUser( user ) );

        user.setSurnames( "   " );
        user.setPassword( "Test" );
        assertFalse( userService.isRightUser( user ) );

        user.setPassword( "   " );
        user.setUsername( "Test" );
        assertFalse( userService.isRightUser( user ) );

        user.setNames( "Test" );
        user.setSurnames( "Test" );
        user.setPassword( "Test" );
        user.setUsername( "Test" );
        assertTrue( userService.isRightUser( user ) );
    }

}
