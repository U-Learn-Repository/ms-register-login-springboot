package co.edu.unal.software_engineering.labs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Private package class for the relation with M...M table
 */

@Entity
@Table( name = "user_role", schema = "public" )
class UserRole implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Attributes
     */

    @EmbeddedId
    private UserRolePK userRolePK;

    /**
     * Constructors
     */

    UserRole( ){
        userRolePK = new UserRolePK( );
    }

    /**
     * Getters and Setters
     */

    User getUser( ){
        return userRolePK.getUser( );
    }

    void setUser( User user ){
        userRolePK.setUser( user );
    }

    Role getRole( ){
        return userRolePK.getRole( );
    }

    void setRole( Role role ){
        userRolePK.setRole( role );
    }

    /**
     * Methods
     */

    @Override
    public boolean equals( Object object ){
        if( !(object instanceof UserRole) ) return false;
        UserRole userRole = (UserRole) object;
        return getUser( ).getId( ).equals( userRole.getUser( ).getId( ) ) &&
                getRole( ).getId( ).equals( userRole.getRole( ).getId( ) );
    }

    @Override
    public int hashCode( ){
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + getUser( ).hashCode( );
        hash = hash * prime + getRole( ).hashCode( );
        return hash;
    }

    /**
     * Private class for primary key
     */

    @Embeddable
    private static class UserRolePK implements Serializable{

        private static final long serialVersionUID = 1L;

        /**
         * Attributes
         */

        @ManyToOne
        @JoinColumn( name = "user_id", insertable = false, updatable = false )
        private User user;

        @ManyToOne
        @JoinColumn( name = "role_id", insertable = false, updatable = false )
        private Role role;

        /**
         * Constructor
         */

        UserRolePK( ){ }

        public UserRolePK( User user, Role role ){
            this.user = user;
            this.role = role;
        }

        /**
         * Getters and Setters
         */

        public User getUser( ){
            return user;
        }

        public void setUser( User user ){
            this.user = user;
        }

        public Role getRole( ){
            return role;
        }

        public void setRole( Role role ){
            this.role = role;
        }
    }

}
