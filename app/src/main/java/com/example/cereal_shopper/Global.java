package com.example.cereal_shopper;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class Global extends Application {
    DatabaseHelper db;
    int currentUserId = 1;      //fake user is the first user created in the db
    DbUser currentUser;

    public void onCreate() {
        super.onCreate();

        db = new DatabaseHelper(getApplicationContext());

        //FIRST TIME YOU RUN THE APP (OR IF YOU CHANGE THE DB VERSION)
        //db.firstStart();

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

}
