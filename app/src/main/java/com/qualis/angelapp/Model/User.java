package com.qualis.angelapp.Model;

public class User {

    private long ID;
    private String email;

    private String firstName;
    private String lastName;

    private String phonenumber;
    private String profilepicname;

    private String token;
    private String usertype;

    public User() {
    }

    public User(long ID, String email, String firstName, String lastName, String phonenumber, String profilepicname, String token, String usertype) {
        this.ID = ID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonenumber = phonenumber;
        this.profilepicname = profilepicname;
        this.token = token;
        this.usertype = usertype;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfilepicname() {
        return profilepicname;
    }

    public void setProfilepicname(String profilepicname) {
        this.profilepicname = profilepicname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}


