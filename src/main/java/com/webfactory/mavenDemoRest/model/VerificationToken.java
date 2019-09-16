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
public class VerificationToken {
    //private static final int EXPIRATION = 60 * 24;
    private static final Duration EXPIRATION = Duration.ofMinutes(60 * 24);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        super();
    }

    public VerificationToken(final String token) {
        //super();

        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public VerificationToken(final String token, final User user) {
        //super();
        //Calendar calendar = Calendar.getInstance();

        this.token = token;
        this.user = user;
        //this.createdDate = new Date(calendar.getTime().getTime());
        this.createdDate = LocalDateTime.now();
        this.expiryDate = calculateExpiryDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /*private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        // calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        // calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }*/

    private LocalDateTime calculateExpiryDate(){
        return this.createdDate.plus(EXPIRATION);
    }

}