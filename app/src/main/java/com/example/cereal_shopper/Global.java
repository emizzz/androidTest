package com.example.cereal_shopper;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Global extends Application {
    DatabaseHelper db;
    int currentUserId = 1;      //fake user is the first user created in the db
    DbUser currentUser;
    int currentGroupId;
    DbProduct barcodeTestProduct;

    public void onCreate() {
        super.onCreate();

        db = new DatabaseHelper(getApplicationContext());

        //FIRST TIME YOU RUN THE APP (OR IF YOU CHANGE THE DB VERSION)

        if(db.isEmpty())
            db.firstStart();

        barcodeTestProduct = createBarcodeTestProduct();

        try{
            currentUser = db.getUser(currentUserId);
        }
        catch(Exception e){
            Log.d("Global", "The fake logged user doesn't exist");
        }

    }
    public DbUser getCurrentUser(){
        return currentUser;
    }
    public DbProduct getBarcodeTestProduct(){
        return barcodeTestProduct;
    }
    public int getCurrentGroupId(){return currentGroupId; }

    public void setCurrentGroupId(int _groupId){
        this.currentGroupId = _groupId;
    }

    public DbProduct createBarcodeTestProduct(){
        //create barcode test product
        List<DbCategory> categories = db.getCategories();
        int barcodeProductCategory = -1;
        for(DbCategory category : categories){
            if(category.getName() == "Cereali, pane, pasta e patate"){
                barcodeProductCategory = category.getId();
                break;
            }
        }
        DbProduct product = new DbProduct("pantry", -1, 0, "Felicetti Grano Duro Integrale Biologico", barcodeProductCategory, 1, 500, 2.03, 1681553088, "");
        return product;
    }
}
