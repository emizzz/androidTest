package com.example.cereal_shopper;

/*
 * class database categories and it's functions
 **/

public class DbCategory {
    int id;
    String name;
    long creation_date;

    public DbCategory(){};
    public DbCategory(String _name) {
        this.name = _name;
    }



    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreationDate(long creation_date) {
        this.creation_date = creation_date;
    }


    //getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public long getCreationDate(){return this.creation_date;}


}
