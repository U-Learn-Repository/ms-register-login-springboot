package co.edu.unal.software_engineering.labs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table( name = "user", schema = "public" )
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Attributes
     */

    @Id
    @SequenceGenerator( name = "USER_USERID_GENERATOR", sequenceName = "public.user_user_id_seq", allocationSize = 1 )
    @GeneratedValue( generator = "USER_USERID_GENERATOR", strategy = GenerationType.SEQUENCE )
    @Column( name = "user_id" )
    private Integer id;

    private String names;

    @JsonIgnore
    private String password;

    private String surnames;

    private String username;

    //bi-directional many-to-many association to Role
    @JsonIgnore
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "user_role", joinColumns = { @JoinColumn( name = "user_id" ) },
            inverseJoinColumns = { @JoinColumn( name = "role_id" ) } )
    private List<Role> roles;

    /**
     * Constructors
     */

    public User( ){
        roles = new ArrayList<>( );
    }

    /**
     * Getters and Setters
     */

    public Integer getId( ){
        return this.id;
    }

    public void setId( Integer id ){
        this.id = id;
    }

    public String getNames( ){
        return this.names;
    }

    public void setNames( String names ){
        this.names = names;
    }

    public String getPassword( ){
        return this.password;
    }

    public void setPassword( String password ){
        this.password = password;
    }

    public String getSurnames( ){
        return this.surnames;
    }

    public void setSurnames( String surnames ){
        this.surnames = surnames;
    }

    public String getUsername( ){
        return this.username;
    }

    public void setUsername( String username ){
        this.username = username;
    }

    public List<Role> getRoles( ){
        return this.roles;
    }

    public void setRoles( List<Role> roles ){
        this.roles = roles;
    }

    /**
     * Methods
     */

    public void addRole( Role role ){
        roles.add( role );
    }

    public boolean hasRole( Role role ){
        return roles.contains( role );
    }

    @Override
    public boolean equals( Object object ){
        if( !(object instanceof User) ) return false;
        return id.equals( ((User) object).getId( ) );
    }

    @Override
    public int hashCode( ){
        return id;
    }

}