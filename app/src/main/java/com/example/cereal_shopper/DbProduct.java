package com.example.cereal_shopper;

import java.util.Date;

/*
 * class database product and it's functions
 **/

public class DbProduct {
    int id;
    String type;
    int group_id;
    int liked;
    String name;
    int category_id;
    int quantity;
    double weight;
    double price;
    long expiry;
    String notes;
    Long creation_date;


    public DbProduct(){}
    public DbProduct(String _type, int _group_id, int _liked, String _name, int _category_id, int _quantity, double _weight, double _price, long _expiry, String _notes) {
        this.type = _type;
        this.group_id = _group_id;
        this.liked = _liked;
        this.name = _name;
        this.category_id = _category_id;
        this.quantity = _quantity;
        this.weight = _weight;
        this.price = _price;
        this.expiry = _expiry;
        this.notes = _notes;
        this.creation_date = new Date().getTime();

    }

    //-------------------setters------------------
    void setId(int _id){
        this.id = _id;
    }
    void setType(String _type){
        this.type = _type;
    }
    void setGroupId(int _group_id){
        this.group_id = _group_id;
    }
    void setLiked(int _liked){
        this.liked = _liked;
    }
    void setName(String _name){
        this.name = _name;
    }
    void setCategoryId(int _category_id){
        this.category_id = _category_id;
    }
    void setQuantity(int _quantity){
        this.quantity = _quantity;
    }
    void setWeight(double _weight){
        this.weight = _weight;
    }
    void setPrice(double _price){
        this.price = _price;
    }
    void setExpiry(long _expiry){
        this.expiry = _expiry;
    }
    void setNotes(String _notes){
        this.notes = _notes;
    }
    void setCreationDate(long _creation_date){
        this.creation_date = _creation_date;
    }

    //------------------getters------------------
    int getId(){
        return id;
    }
    String getType(){
        return type;
    }
    int getGroupId(){
        return group_id;
    }
    int getLiked(){
        return liked;
    }
    String getName(){
        return name;
    }
    int getCategoryId(){
        return category_id;
    }
    int getQuantity(){
        return quantity;
    }
    double getWeight(){ return weight; }
    double getPrice(){
        return price;
    }
    long getExpiry(){
        return expiry;
    }
    String getNotes(){
        return notes;
    }
    long getCreationDate(){
        return creation_date;
    }


}
