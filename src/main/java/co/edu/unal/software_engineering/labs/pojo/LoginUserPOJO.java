package co.edu.unal.software_engineering.labs.pojo;

/**
 * Plain Old Java Object for Login
 */
public class LoginUserPOJO{

    private String username;

    private String password;

    public String getUsername( ){
        return username;
    }

    public void setUsername( String username ){
        this.username = username;
    }

    public String getPassword( ){
        return password;
    }

    public void setPassword( String password ){
        this.password = password;
    }
}
