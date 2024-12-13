package com.teaspiritspringboot.teaspiritspringboot.model;

import java.io.Serializable;

import jakarta.persistence.Column;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    private Long id;

    @Column (name = "username", nullable = true)
    private String username;

    @Column (name = "firstname", nullable = true)
    private String firstname;

    @Column (name = "lastname", nullable = true)
    private String lastname;
    
    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "hashedPassword", nullable = false)
    private String hashedPassword;

    @Column (name = "role", nullable = false)
    private String role;

    public User(){}

    public User(String username, String email, String hashedPassword, String role){
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;

    }

    public User(String email, String hashedPassword, String role) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public User(String username,String firstname, String lastname, String email, String role){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;

    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    // }





    
}
