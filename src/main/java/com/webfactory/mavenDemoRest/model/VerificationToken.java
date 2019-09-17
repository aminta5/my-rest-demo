package com.webfactory.mavenDemoRest.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import javax.persistence.*;

@Entity
@Table(name = "verification_token")
public class VerificationToken extends BaseEntity{
    private static final Duration EXPIRATION = Duration.ofMinutes(60 * 24);

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    @Column(name="token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="expiry_date")
    private LocalDateTime expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public VerificationToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.expiryDate = calculateExpiryDate();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    private LocalDateTime calculateExpiryDate(){
        return this.createdDate.plus(EXPIRATION);
    }

}