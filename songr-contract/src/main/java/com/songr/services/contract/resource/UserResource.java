package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mysql.cj.jdbc.Blob;

@JsonPropertyOrder({"id", "userName", "firstName", "middleName", "lastName", "email", "password", "dateOfBirth", "gender", "profileImage", "token", "spotify_refresh_token"})
public class UserResource {
    @JsonProperty("id")
    private int id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("profileImage")
    private Blob profileImage;
    @JsonProperty("token")
    private String token;
    @JsonProperty("spotify_refresh_token")
    private String spotify_refresh_token;


    public UserResource() { /*needed for Jackson*/ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Blob getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Blob profileImage) {
        this.profileImage = profileImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSpotify_refresh_token() {
        return spotify_refresh_token;
    }

    public void setSpotify_refresh_token(String spotify_refresh_token) {
        this.spotify_refresh_token = spotify_refresh_token;
    }
}
