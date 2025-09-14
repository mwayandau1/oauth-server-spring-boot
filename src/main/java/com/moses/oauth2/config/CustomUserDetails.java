package com.moses.oauth2.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * Custom user details implementation for Spring Security.
 *
 * This class represents a custom user details object that can be used by the Spring Security framework. It provides
 * methods to retrieve information about the authenticated user such as their username (email address), password, and
 * granted authorities (permissions).
 *
 * The constructor of this class takes in several parameters: - userId: A string representing the unique identifier of
 * the user. - email: A string representing the email address of the user. - password: A string representing the hashed
 * password of the user. - isAccountLocked: A boolean indicating whether or
 */
@Data
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userId, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }



}
