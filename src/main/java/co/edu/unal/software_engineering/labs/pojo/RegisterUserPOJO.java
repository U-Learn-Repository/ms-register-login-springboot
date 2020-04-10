package co.edu.unal.software_engineering.labs.pojo;

public class RegisterUserPOJO{

    private String names;
    private String password;
    private String surnames;
    private String username;



    public String getNames( ){
        return names;
    }

    public void setNames( String names ){
        this.names = names;
    }

    public String getPassword( ){
        return password;
    }

    public void setPassword( String password ){
        this.password = password;
    }

    public String getSurnames( ){
        return surnames;
    }

    public void setSurnames( String surnames ){
        this.surnames = surnames;
    }

    public String getUsername( ){
        return username;
    }

    public void setUsername( String username ){
        this.username = username;
    }
}
