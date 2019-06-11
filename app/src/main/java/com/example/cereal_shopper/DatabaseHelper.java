package com.example.cereal_shopper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


//database implementation
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 27;

    // Database Name
    private static final String DATABASE_NAME = "cerealShopper";

    // Table Names
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CATEGORIES = "categories";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // GROUPS Table - column names
    private static final String KEY_GROUP_TITLE = "title";

    // USERS Table - column names
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_BALANCE = "balance";
    private static final String KEY_USER_GROUP_IDS = "group_id";

    // PRODUCTS Table - column names
    private static final String KEY_PRODUCT_TYPE = "product_type";
    private static final String KEY_PRODUCT_GROUP_ID = "group_id";
    private static final String KEY_PRODUCT_LIKED = "liked";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_CATEGORY_ID = "category_id";
    private static final String KEY_PRODUCT_QUANTITY = "quantity";
    private static final String KEY_PRODUCT_WEIGHT = "weight";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_PRODUCT_EXPIRY = "expiry";
    private static final String KEY_PRODUCT_NOTES = "notes";

    // CATEGORIES Table - column names
    private static final String KEY_CATEGORIES_NAME = "name";


    // GROUPS table create statement
    private static final String CREATE_TABLE_GROUPS = "CREATE TABLE " + TABLE_GROUPS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_GROUP_TITLE + " TEXT,"
            + KEY_CREATED_AT + " INTEGER" + ")";

    // USERS table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_NAME + " TEXT,"
            + KEY_USER_EMAIL + " TEXT,"
            + KEY_USER_BALANCE + " INTEGER,"
            + KEY_USER_GROUP_IDS + " TEXT,"
            + KEY_CREATED_AT + " INTEGER" + ")";

    // PRODUCTS table create statement
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PRODUCT_TYPE + " TEXT,"
            + KEY_PRODUCT_GROUP_ID + " INTEGER,"
            + KEY_PRODUCT_LIKED + " INTEGER,"
            + KEY_PRODUCT_NAME + " TEXT,"
            + KEY_PRODUCT_CATEGORY_ID + " INTEGER,"
            + KEY_PRODUCT_QUANTITY + " INTEGER,"
            + KEY_PRODUCT_WEIGHT + " REAL,"
            + KEY_PRODUCT_PRICE + " REAL,"
            + KEY_PRODUCT_EXPIRY + " INTEGER,"
            + KEY_PRODUCT_NOTES + " TEXT,"
            + KEY_CREATED_AT + " INTEGER" + ")";

    // CATEGORIES table create statement
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_CATEGORIES_NAME + " TEXT,"
            + KEY_CREATED_AT + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_GROUPS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        // create new tables
        onCreate(db);
    }

    /*
    * This method should be called only the first time the app runs
    * It creates:
     *  -a fake logged user
     *  -2 groups
     *  -some other users
     *  -some products
    * */
    public DbUser firstStart(){

        //create a fake logged in user (id == 1)
        ArrayList<Integer> group_ids = new ArrayList<>();
        group_ids.add(1);
        group_ids.add(2);
        DbUser currentUser = new DbUser("Matteo", "matteo@mattei.it", 0, group_ids);
        this.createUser(currentUser);


        //create 2 groups
        DbGroup group = new DbGroup("group 1");
        long new_group_id = this.createGroup(group);
        DbGroup group2 = new DbGroup("group 2");
        long new_group_id2 = this.createGroup(group2);

        //create 2 users
        ArrayList<Integer> group_ids1 = new ArrayList<>();
        group_ids1.add(1);
        DbUser newUser1 = new DbUser("Marco", "marchi@lauri.it", 0, group_ids1);
        this.createUser(newUser1);

        ArrayList<Integer> group_ids2 = new ArrayList<>();
        group_ids2.add(2);
        DbUser newUser2 = new DbUser("Lucia", "lucia@lauri.it", 0, group_ids2);
        this.createUser(newUser2);


        //create categories
        this.createCategory(new DbCategory("Cereali, pane, pasta e patate"));
        this.createCategory(new DbCategory("Frutta e verdura"));
        this.createCategory(new DbCategory("Latte, yogurt e formaggi"));
        this.createCategory(new DbCategory("Carne, pesce uova e legumi"));
        this.createCategory(new DbCategory("Grassi e oli da condimento"));

        //create products
        DbProduct product1 =  new DbProduct();
        DbProduct product2 =  new DbProduct();
        DbProduct product3 = new DbProduct();
        DbProduct product4 = new DbProduct();

        List<DbCategory> categories = this.getCategories();
        for(DbCategory cat : categories){
            if(cat.getName().equals("Cereali, pane, pasta e patate")){
                product2 = new DbProduct("pantry", 1, 1, "Pasta", cat.getId(), 2, 2000, 3.99, 1567517309, "");
                product3 = new DbProduct("shopping_list", 1, 0, "Pan bauletto", cat.getId(), 1, -1, -1, -1, "");
            }
            else if(cat.getName().equals("Latte, yogurt e formaggi")){
                product1 = new DbProduct("pantry", 1, 0, "Latte", cat.getId(), 2, 1000.43, 2.73, 1567517309, "");
                product4 = new DbProduct("shopping_list", 1, 0, "Formaggio grana", cat.getId(), 1, -1, -1, -1, "");
            }
        }
        this.createProduct(product1);
        this.createProduct(product2);
        this.createProduct(product3);
        this.createProduct(product4);

        return currentUser;
    }

    //---------------------------------------------GROUPS METHODS---------------------------------------------

    /*
    * EXAMPLE
    * DbGroup group = new DbGroup("group name example");
    * long new_group_id = db.createGroup(group);
    */
    public long createGroup(DbGroup _group) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_TITLE, _group.getTitle());
        values.put(KEY_CREATED_AT, _group.getCreationDate());

        long group_id = db.insert(TABLE_GROUPS, null, values);

        return group_id;
    }

    public DbGroup getGroup(int _id){
        DbGroup new_group_copy = new DbGroup();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS + " WHERE " + KEY_ID +  " = " + _id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ){
            new_group_copy.setId( cursor.getInt( cursor.getColumnIndex(KEY_ID)) );
            new_group_copy.setTitle( cursor.getString( cursor.getColumnIndex(KEY_GROUP_TITLE)) );
            new_group_copy.setCreationDate( cursor.getLong( cursor.getColumnIndex(KEY_CREATED_AT)) );
        }

        return new_group_copy;
    }

    public int updateGroup(DbGroup _group){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_TITLE, _group.getTitle());

        int group_id = db.update(TABLE_GROUPS, values, KEY_ID + "=" + _group.getId(),null);
        return group_id;
    }

    public List<DbGroup> getGroups() {
        List<DbGroup> groups = new ArrayList<DbGroup>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS + " ORDER BY " + KEY_GROUP_TITLE +  " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DbGroup _group = new DbGroup();
                _group.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                _group.setTitle((c.getString(c.getColumnIndex(KEY_GROUP_TITLE))));
                _group.setCreationDate(c.getInt(c.getColumnIndex(KEY_CREATED_AT)));

                groups.add(_group);

            } while (c.moveToNext());
        }

        return groups;
    }


    //---------------------------------------------USERS METHODS---------------------------------------------

    /*
     * EXAMPLE
     *  DbUser newUser = new DbUser("Gianni", "gianni@gianni.it", 13, 9);
     *  long new_user_id = db.createUser(newUser);
     */
    public long createUser(DbUser _user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, _user.getName());
        values.put(KEY_USER_EMAIL, _user.getEmail());
        values.put(KEY_USER_BALANCE, _user.getBalance());
        values.put(KEY_USER_GROUP_IDS, _user.getSerializedGroupId());
        values.put(KEY_CREATED_AT, _user.getCreationDate());

        long user_id = db.insert(TABLE_USERS, null, values);
        return user_id;
    }

    public List<DbUser> getUsers() {
        List<DbUser> users = new ArrayList<DbUser>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " ORDER BY " + KEY_USER_NAME +  " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DbUser _user = new DbUser();
                _user.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                _user.setName((c.getString(c.getColumnIndex(KEY_USER_NAME))));
                _user.setEmail((c.getString(c.getColumnIndex(KEY_USER_EMAIL))));
                _user.setBalance((c.getDouble(c.getColumnIndex(KEY_USER_BALANCE))));
                _user.setSerializedGroupId((c.getString(c.getColumnIndex(KEY_USER_GROUP_IDS))));
                _user.setCreationDate(c.getInt(c.getColumnIndex(KEY_CREATED_AT)));

                users.add(_user);

            } while (c.moveToNext());
        }

        return users;
    }

    public DbUser getUser(int _id){
        DbUser new_user_copy = new DbUser();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_ID +  " = " + _id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ){
            new_user_copy.setId( cursor.getInt( cursor.getColumnIndex(KEY_ID)) );
            new_user_copy.setName( cursor.getString( cursor.getColumnIndex(KEY_USER_NAME)) );
            new_user_copy.setEmail( cursor.getString( cursor.getColumnIndex(KEY_USER_EMAIL)) );
            new_user_copy.setBalance( cursor.getDouble( cursor.getColumnIndex(KEY_USER_BALANCE)) );
            new_user_copy.setSerializedGroupId( cursor.getString( cursor.getColumnIndex(KEY_USER_GROUP_IDS)) );
            new_user_copy.setCreationDate( cursor.getLong( cursor.getColumnIndex(KEY_CREATED_AT)) );
        }

        return new_user_copy;
    }

    public int updateUser(DbUser _user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, _user.getId());
        values.put(KEY_USER_NAME, _user.getName());
        values.put(KEY_USER_EMAIL, _user.getEmail());
        values.put(KEY_USER_BALANCE, _user.getBalance());
        values.put(KEY_USER_GROUP_IDS, _user.getSerializedGroupId());
        values.put(KEY_CREATED_AT, _user.getCreationDate());

        int user_id = db.update(TABLE_USERS, values, KEY_ID + "=" + _user.getId(),null);

        return user_id;
    }

    public List<DbGroup> getUserGroups(int _id){
        List<DbGroup> groups = new ArrayList<DbGroup>();
        DbUser userUtility = new DbUser();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_ID +  " = " + _id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor != null && cursor.moveToFirst() ){
            userUtility.setSerializedGroupId( cursor.getString(cursor.getColumnIndex(KEY_USER_GROUP_IDS)) );
            ArrayList<Integer> listOfUserGroupIds = userUtility.getGroupIds();

            for( int group_id : listOfUserGroupIds ){
                groups.add( this.getGroup(group_id) );
            }
        }
        return groups;
    }



    //---------------------------------------------PRODUCTS METHODS---------------------------------------------
    public long createProduct(DbProduct _product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_TYPE, _product.getType());
        values.put(KEY_PRODUCT_GROUP_ID, _product.getGroupId());
        values.put(KEY_PRODUCT_LIKED, _product.getLiked());
        values.put(KEY_PRODUCT_NAME, _product.getName());
        values.put(KEY_PRODUCT_CATEGORY_ID, _product.getCategoryId());
        values.put(KEY_PRODUCT_QUANTITY, _product.getQuantity());
        values.put(KEY_PRODUCT_WEIGHT, _product.getWeight());
        values.put(KEY_PRODUCT_PRICE, _product.getPrice());
        values.put(KEY_PRODUCT_EXPIRY, _product.getExpiry());
        values.put(KEY_PRODUCT_NOTES, _product.getNotes());
        values.put(KEY_CREATED_AT, _product.getCreationDate());

        long product_id = db.insert(TABLE_PRODUCTS, null, values);
        return product_id;
    }

    public DbProduct getProduct(int _id){
        DbProduct new_product_copy = new DbProduct();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_ID +  " = " + _id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if( c != null && c.moveToFirst() ){
            new_product_copy.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            new_product_copy.setType(c.getString((c.getColumnIndex(KEY_PRODUCT_TYPE))));
            new_product_copy.setGroupId(c.getInt((c.getColumnIndex(KEY_PRODUCT_GROUP_ID))));
            new_product_copy.setLiked(c.getInt((c.getColumnIndex(KEY_PRODUCT_LIKED))));
            new_product_copy.setName(c.getString((c.getColumnIndex(KEY_PRODUCT_NAME))));
            new_product_copy.setCategoryId(c.getInt((c.getColumnIndex(KEY_PRODUCT_CATEGORY_ID))));
            new_product_copy.setQuantity(c.getInt((c.getColumnIndex(KEY_PRODUCT_QUANTITY))));
            new_product_copy.setWeight(c.getInt((c.getColumnIndex(KEY_PRODUCT_WEIGHT))));
            new_product_copy.setPrice(c.getInt((c.getColumnIndex(KEY_PRODUCT_PRICE))));
            new_product_copy.setExpiry(c.getInt((c.getColumnIndex(KEY_PRODUCT_EXPIRY))));
            new_product_copy.setNotes(c.getString((c.getColumnIndex(KEY_PRODUCT_NOTES))));
            new_product_copy.setCreationDate(c.getInt((c.getColumnIndex(KEY_CREATED_AT))));
        }

        return new_product_copy;
    }

    public int updateProduct(DbProduct _product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_TYPE, _product.getType());
        values.put(KEY_PRODUCT_GROUP_ID, _product.getGroupId());
        values.put(KEY_PRODUCT_LIKED, _product.getLiked());
        values.put(KEY_PRODUCT_NAME, _product.getName());
        values.put(KEY_PRODUCT_CATEGORY_ID, _product.getCategoryId());
        values.put(KEY_PRODUCT_QUANTITY, _product.getQuantity());
        values.put(KEY_PRODUCT_WEIGHT, _product.getWeight());
        values.put(KEY_PRODUCT_PRICE, _product.getPrice());
        values.put(KEY_PRODUCT_EXPIRY, _product.getExpiry());
        values.put(KEY_PRODUCT_NOTES, _product.getNotes());
        values.put(KEY_CREATED_AT, _product.getCreationDate());

        int product_id = db.update(TABLE_PRODUCTS, values, KEY_ID + "=" + _product.getId(),null);

        return product_id;
    }

    public void deleteProduct(int product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[] { String.valueOf(product_id) });
    }

    public ArrayList<DbProduct> getProducts(int _group_id, String _type) {
        ArrayList<DbProduct> products = new ArrayList<DbProduct>();

        if(_type == "shopping_list" || _type == "pantry"){

            String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +  KEY_PRODUCT_GROUP_ID + " = '" + _group_id + "' AND " + KEY_PRODUCT_TYPE + " = '" + _type + "' ORDER BY " + KEY_USER_NAME +  " ASC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    DbProduct _product = new DbProduct();
                    _product.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    _product.setType(c.getString((c.getColumnIndex(KEY_PRODUCT_TYPE))));
                    _product.setGroupId(c.getInt((c.getColumnIndex(KEY_PRODUCT_GROUP_ID))));
                    _product.setLiked(c.getInt((c.getColumnIndex(KEY_PRODUCT_LIKED))));
                    _product.setName(c.getString((c.getColumnIndex(KEY_PRODUCT_NAME))));
                    _product.setCategoryId(c.getInt((c.getColumnIndex(KEY_PRODUCT_CATEGORY_ID))));
                    _product.setQuantity(c.getInt((c.getColumnIndex(KEY_PRODUCT_QUANTITY))));
                    _product.setWeight(c.getInt((c.getColumnIndex(KEY_PRODUCT_WEIGHT))));
                    _product.setPrice(c.getInt((c.getColumnIndex(KEY_PRODUCT_PRICE))));
                    _product.setExpiry(c.getInt((c.getColumnIndex(KEY_PRODUCT_EXPIRY))));
                    _product.setNotes(c.getString((c.getColumnIndex(KEY_PRODUCT_NOTES))));
                    _product.setCreationDate(c.getInt((c.getColumnIndex(KEY_CREATED_AT))));

                    products.add(_product);

                } while (c.moveToNext());
            }
        }
        else{
            Log.d("DatabaseHelper", "getProducts: wrong product type");
        }

        return products;
    }

    /*
    * the favorites logic is not implemented, is only a fake functionality
    * */
    public List<DbProduct> getFavoritesProducts(int _groupId){
        List<DbProduct> products = new ArrayList<DbProduct>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_PRODUCT_GROUP_ID +  " = " + _groupId + " AND " + KEY_PRODUCT_LIKED + " = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DbProduct _product = new DbProduct();
                _product.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                _product.setType(c.getString((c.getColumnIndex(KEY_PRODUCT_TYPE))));
                _product.setGroupId(c.getInt((c.getColumnIndex(KEY_PRODUCT_GROUP_ID))));
                _product.setLiked(c.getInt((c.getColumnIndex(KEY_PRODUCT_LIKED))));
                _product.setName(c.getString((c.getColumnIndex(KEY_PRODUCT_NAME))));
                _product.setCategoryId(c.getInt((c.getColumnIndex(KEY_PRODUCT_CATEGORY_ID))));
                _product.setQuantity(c.getInt((c.getColumnIndex(KEY_PRODUCT_QUANTITY))));
                _product.setWeight(c.getInt((c.getColumnIndex(KEY_PRODUCT_WEIGHT))));
                _product.setPrice(c.getInt((c.getColumnIndex(KEY_PRODUCT_PRICE))));
                _product.setExpiry(c.getInt((c.getColumnIndex(KEY_PRODUCT_EXPIRY))));
                _product.setNotes(c.getString((c.getColumnIndex(KEY_PRODUCT_NOTES))));
                _product.setCreationDate(c.getInt((c.getColumnIndex(KEY_CREATED_AT))));

                products.add(_product);

            } while (c.moveToNext());
        }
        return products;
    }

    //---------------------------------------------CATEGORIES METHODS---------------------------------------------

    public long createCategory(DbCategory _category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIES_NAME, _category.getName());
        values.put(KEY_CREATED_AT, _category.getCreationDate());

        long category_id = db.insert(TABLE_CATEGORIES, null, values);
        return category_id;
    }


    public List<DbCategory> getCategories() {
        List<DbCategory> categories = new ArrayList<DbCategory>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES + " ORDER BY " + KEY_CATEGORIES_NAME +  " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DbCategory _category = new DbCategory();
                _category.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                _category.setName((c.getString(c.getColumnIndex(KEY_CATEGORIES_NAME))));
                _category.setCreationDate(c.getInt(c.getColumnIndex(KEY_CREATED_AT)));
                categories.add(_category);

            } while (c.moveToNext());
        }

        return categories;
    }
 // checks if the database is empty, it's called at the beginning to check if the database needs to be populated with tests items
    public boolean isEmpty(){
        String count = "SELECT  count(*) FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(count, null);
        if (c.moveToFirst()) {
            int j = c.getInt(0);
            if(j>0) return false;
            else return true;
        }
        return true;
    }
}

