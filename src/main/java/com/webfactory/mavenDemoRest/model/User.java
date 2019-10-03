package com.webfactory.mavenDemoRest.model;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webfactory.mavenDemoRest.validation.ValidEmail;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {

    @Size(min = 1, max = 40)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1, max = 40)
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 1, max = 20)
    @NotNull
    @Column(unique = true, name = "nickname")
    private String nickname;

    @Column(unique = true, name = "email")
    @ValidEmail
    @Size(max = 120)
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "no unique characters allowed")
    @Size(min = 8, max = 12)
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Location location;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    //constructors
    /*public User() {
    }*/

    public User(String firstName, String lastName, String nickname, String email, String password, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    @Builder
    public User(Long id, String firstName, String lastName, String nickname, String email, String password, List<Post> posts, Location location, UserType userType) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.location = location;
        this.userType = userType;
    }
}
