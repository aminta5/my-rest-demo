package com.webfactory.mavenDemoRest.requestBodies;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.validation.FieldMatch;
import com.webfactory.mavenDemoRest.validation.ValidEmail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Validated
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")})
public class RequestBodyUser {

    private Long id;

    @Size(min = 1, max = 40)
    private String firstName;

    @Size(min = 1, max = 40)
    private String lastName;

    /*@NotNull
    @Size(max = 20)
    private String nickname;*/

    @ValidEmail
    @NotNull
    @Size(max = 20)
    private String email;

    @NotNull
    @Size(min = 8, max = 12)
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull
    private boolean enabled;

    private List<RequestBodyPost> posts = new ArrayList<>();
    private RequestBodyLocation location;
    private UserType userType;


    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RequestBodyPost> getPost() {
        return posts;
    }

    public void setPost(List<RequestBodyPost> post) {
        this.posts = post;
    }

    public RequestBodyLocation getLocation() {
        return location;
    }

    public void setLocation(RequestBodyLocation location) {
        this.location = location;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
