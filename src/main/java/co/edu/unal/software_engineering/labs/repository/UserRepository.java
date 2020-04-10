package co.edu.unal.software_engineering.labs.repository;

import co.edu.unal.software_engineering.labs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername( String username );

}
