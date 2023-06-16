package com.netflix.entities;

import com.netflix.dto.UserDto;
import com.netflix.enums.AccountStatus;
import com.netflix.enums.Region;
import com.netflix.enums.SubscriptionPlan;
import com.netflix.security.ApplicationUserRole;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "subscription_plan", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private ApplicationUserRole applicationUserRole;

    public User() {
    }

    public User(String username, String email, String password, SubscriptionPlan subscriptionPlan, AccountStatus accountStatus, Region region, ApplicationUserRole applicationUserRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.subscriptionPlan = subscriptionPlan;
        this.accountStatus = accountStatus;
        this.region = region;
        this.applicationUserRole = applicationUserRole;
    }

    public User(String username, String email, String password, SubscriptionPlan subscriptionPlan, AccountStatus accountStatus, Region region) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.subscriptionPlan = subscriptionPlan;
        this.accountStatus = accountStatus;
        this.region = region;
    }

    public User(UserDto userDTO) {
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.subscriptionPlan = userDTO.getSubscriptionPlan();
        this.accountStatus = userDTO.getAccountStatus();
        this.region = userDTO.getRegion();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    public ApplicationUserRole getApplicationUserRole() {
        return applicationUserRole;
    }

    public void setApplicationUserRole(ApplicationUserRole applicationUserRole) {
        this.applicationUserRole = applicationUserRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", subscriptionPlan=" + subscriptionPlan +
                ", accountStatus=" + accountStatus +
                ", region=" + region +
                ", applicationUserRole=" + applicationUserRole +
                '}';
    }
}
