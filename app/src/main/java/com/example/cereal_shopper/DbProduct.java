package com.example.cereal_shopper;

public class DbProduct {
    String type;
    int group_id;
    int liked;
    String name;
    int category_id;
    int quantity;
    int weight;
    int price;
    int expiry;
    String notes;


    public DbProduct(String _type, int _group_id, int _liked, String _name, int _category_id, int _quantity, int _weight, int _price, int _expiry, String _notes) {
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

    }

}
