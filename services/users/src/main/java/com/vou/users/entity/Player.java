package com.vou.users.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
@DiscriminatorValue("player")
public class Player extends User {

    @Column(name = "gender")
    private String gender;

    @Column(name = "facebook_account")
    private String facebookAccount;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "turns")
    private int turns;


    // Constructors, getters, and setters

    public Player() {
    }

    public Player(String gender, String facebookAccount, String dateOfBirth, String avatar, int turns) {
        this.gender = gender;
        this.facebookAccount = facebookAccount;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.turns = turns;
    }

    public Player(String fullName, String username, String accountId, String email, String phone, UserRole role, boolean status, String gender, String facebookAccount, String dateOfBirth, String avatar, int turns) {
        super(fullName, username, accountId, email, phone, role, status);
        this.gender = gender;
        this.facebookAccount = facebookAccount;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.turns = turns;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }



    @Override
    public String toString() {
        return "Player{" +
                "gender='" + gender + '\'' +
                ", facebookAccount='" + facebookAccount + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", avatar='" + avatar + '\'' +
                ", turns=" + turns +
                '}';
    }
}
