package com.qualis.qfood.Model;

public class User {

    private long ID;
    private String email;

    private String firstname;
    private String lastname;

    private String phonenumber;
    private String profilepicname;

    private String token;
    private String usertype;

    public User() {
    }

    public User(long ID, String email, String firstname, String lastname, String phonenumber, String profilepicname, String token, String usertype) {
        this.ID = ID;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.profilepicname = profilepicname;
        this.token = token;
        this.usertype = usertype;
    }


    public long getId() {
        return ID;
    }

    public void setId(long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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


