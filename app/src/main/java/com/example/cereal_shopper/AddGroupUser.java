package com.example.cereal_shopper;
import ir.mirrajabi.searchdialog.core.Searchable;


/*
 *element that help the user search in the database of users and get the person they are searching for
 **/
public class AddGroupUser implements Searchable {
    private DbUser user;
    private String title;

    public AddGroupUser(DbUser _user) {
        this.user = _user;
        this.title = _user.getName();
    }

    public DbUser getUser() {
        return this.user;
    }
    @Override
    public String getTitle() {
        return this.title;
    }

    public AddGroupUser setUser(DbUser _user) {
        user = _user;
        return this;
    }

}