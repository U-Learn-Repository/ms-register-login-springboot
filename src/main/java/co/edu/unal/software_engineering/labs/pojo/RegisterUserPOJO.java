package co.edu.unal.software_engineering.labs.pojo;

public class RegisterUserPOJO{

    private  int id;
    private String names;
    private int id_documment;
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

    public int getId_documment() {
        return id_documment;
    }

    public void setId_documment(int id_documment) {
        this.id_documment = id_documment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
