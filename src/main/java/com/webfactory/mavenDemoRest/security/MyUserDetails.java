package com.webfactory.mavenDemoRest.security;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private List<Post> posts;
    private String password;
    private String username;
    private UserType userType;

    public MyUserDetails(User user) {
        this.username = user.getNickname();
        this.password = user.getPassword();
        this.posts = user.getPosts();
        this.userType = user.getUserType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userType.toString()));
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

    //posts
    /*public List<Post> getPosts() {
        return posts;
    }*/
}
