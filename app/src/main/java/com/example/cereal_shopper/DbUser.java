package com.example.cereal_shopper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DbUser {
    int id;
    String name;
    String email;
    int balance;
    ArrayList<Integer> group_ids;
    long creation_date;
    String serialized_group_ids;
    //byte[] photo;

    public DbUser(){}

    public DbUser(String name, String email, int _balance, ArrayList<Integer> _group_ids) {
        this.name = name;
        this.email = email;
        this.balance = _balance;
        //this.photo = _photo;
        this.creation_date = new Date().getTime();

        this.group_ids = _group_ids;
        this.serialized_group_ids = listToString(_group_ids);

    }
    // setters
    public void setId(int _id) {
        this.id = _id;
    }
    public void setName(String _name) {
        this.name = _name;
    }
    public void setCreationDate(long _date) {
        this.creation_date = _date;
    }
    public void setEmail(String _email) {
        this.email = _email;
    }
    public void setBalance(int _balance) {
        this.balance = _balance;
    }
    public void setGroupId(ArrayList<Integer> _group_ids) {
        this.group_ids = _group_ids;
        this.serialized_group_ids = listToString(_group_ids);
    }
    public void setSerializedGroupId(String _group_ids){
        this.serialized_group_ids = _group_ids;
        this.group_ids = stringToList(_group_ids);
    }

    // getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public long getCreationDate() {
        return this.creation_date;
    }
    public String getEmail() {
        return this.email;
    }
    public int getBalance() {
        return this.balance;
    }
    public ArrayList<Integer> getGroupIds() {
        return this.group_ids;
    }
    public String getSerializedGroupId() {
        return this.serialized_group_ids;
    }

    //utilities
    public String listToString(ArrayList<Integer> _group_ids){
        StringBuilder newString  = new StringBuilder();
        Iterator<Integer> iterator = _group_ids.iterator();
        while(iterator.hasNext())
        {
            newString.append(iterator.next());
            if(iterator.hasNext()){
                newString.append(",");
            }
        }
        return newString.toString();
    }
    public ArrayList<Integer> stringToList(String stringValue){
        // To ArrayList
        List<String> stringList = new ArrayList<>(Arrays.asList(stringValue.split(",")));

        ArrayList<Integer> intList = new ArrayList<>();

        for(int i = 0; i < stringList.size(); i++) {
            try{
                intList.add(Integer.parseInt(stringList.get(i)));
            }
            catch(Exception e){

            }
        }

        return intList;
    }
}
