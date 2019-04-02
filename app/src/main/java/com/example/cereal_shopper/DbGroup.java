package com.example.cereal_shopper;
import java.util.Date;

public class DbGroup {
    int id;
    String title;
    long creation_date;

    //constructors
    public DbGroup() { }
    public DbGroup(String title) {
        this.title = title;
        this.creation_date = new Date().getTime();
    }

    // setters
    public void setId(int _id) { this.id = _id; }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCreationDate(long _date) {
        this.creation_date = _date;
    }

    // getters
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public long getCreationDate() {
        return this.creation_date;
    }

}
