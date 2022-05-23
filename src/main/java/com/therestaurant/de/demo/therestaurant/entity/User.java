package com.therestaurant.de.demo.therestaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 4, max = 50)
    @NotNull
    private String username;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50)
    @NotNull
    private String firstname;

    @Column(nullable = false, length = 50)
    @Size(min = 4, max = 50)
    @NotNull
    private String lastname;

    @Column(nullable = true, length = 100)
    @NotNull
    private String address;

    @Column(nullable = false)
    @Size(min = 0, max = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String role;

    @Transient
    private String repeatPassword;

    public User(String username, String password, String firstname, String lastname, String address, String role)
    {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if(role == "") return null;

        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean getIsAdmin() {
        return  role.equals("ADMIN");
    }
}
