package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class liste extends AppCompatActivity {

    ViewPager vPager;
    PageAdapter pAdapter;
    TabLayout tabLayout;
    Toolbar toolbar;
    public static JSONArray listList = new JSONArray();
    public static JSONArray pantryList = new JSONArray();
    private static final int REQUEST_CODE_LISTA = 10;
    private static final int REQUEST_CODE_DISPENSA = 20;
    private static final int REQUEST_CODE_RECAP = 40;


    static RecyclerView groupsListViewList;
    static RecyclerView.Adapter groupsListAdapterList;
    static RecyclerView.LayoutManager mLayoutManagerList;

    static RecyclerView groupsListViewPant;
    static RecyclerView.Adapter groupsListAdapterPant;
    static RecyclerView.LayoutManager mLayoutManagerPant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        vPager = (ViewPager) findViewById(R.id.pager);
        pAdapter = new PageAdapter(getSupportFragmentManager());
        vPager.setAdapter(pAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vPager);

        JSONObject item111 = new JSONObject();
        JSONObject item222 = new JSONObject();
        JSONObject item333 = new JSONObject();

        try {
            item111.put("type", "product_list");
            item111.put("title", "Caffe");
            item111.put("description", "mocha");
            item111.put("quantity", 2);
            item111.put("weight", 100);
            item111.put("price", "3.50€");
            item111.put("expiration", "2/05/20");      // "list" or "pantry"
            item111.put("product_type", "pantry");      // "list" or "pantry"
            item222.put("type", "product_list");
            item222.put("title", "Pasta");
            item222.put("description", "Spaghetti");
            item222.put("quantity", 3);
            item222.put("weight", 500);
            item222.put("price", "1.50€");
            item222.put("expiration", "12/02/20");      // "list" or "pantry"
            item222.put("product_type", "pantry");      // "list" or "pantry"

            item333.put("type", "product_list");
            item333.put("title", "Latte");
            item333.put("description", "parzialmente scremato");
            item333.put("quantity", 3);
            item333.put("product_type", "list");      // "list" or "pantry"

            listList.put(item333);
            pantryList.put(item111);
            pantryList.put(item222);

        } catch (JSONException e) {
            //log something
        }

    }

    public static class PageAdapter extends FragmentPagerAdapter {

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lista della Spesa";
                case 1:
                    return "Dispensa";
                default:
                    return "Error";
            }
        }

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:

                    Fragment f1 = new ListaSpesa();
                    return f1;
                case 1:
                    Fragment f2 = new Dispensa();
                    return f2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_LISTA) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String name = extras.getString("NAME");
                String category = extras.getString("CATEGORY");
                int quantity = extras.getInt("QUANTITY");
                String note = extras.getString("NOTE");
                Toast.makeText(getApplicationContext(), name + category + quantity + note, Toast.LENGTH_SHORT).show();
                JSONObject item = new JSONObject();
                try {
                    item.put("type", "product_list");
                    item.put("product_type", "list");      // "list" or "pantry"
                    item.put("title", name);
                    item.put("description", note);

                    listList.put(item);

                } catch (JSONException e) {
                    //log something
                }

                groupsListAdapterList = new CustomList(this, listList);

                groupsListViewList.setAdapter(groupsListAdapterList);

                // and get whatever data you are adding
            }
        }
        if (requestCode == REQUEST_CODE_DISPENSA) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String name = extras.getString("NAME");
                String category = extras.getString("CATEGORY");
                int quantity = extras.getInt("QUANTITY");
                int weight = extras.getInt("WEIGHT");
                String price = extras.getString("PRICE");
                String expiration = extras.getString("EXPIRATION");
                String note = extras.getString("NOTE");
                JSONObject item = new JSONObject();
                try {
                    item.put("type", "product_list");
                    item.put("product_type", "pantry");      // "list" or "pantry"
                    item.put("title", name);
                    item.put("quantity", quantity);
                    item.put("weight", weight);
                    item.put("price", price);
                    item.put("expiration", expiration);
                    item.put("description", note);

                    pantryList.put(item);

                } catch (JSONException e) {
                    //log something
                }
                int ii = 0;
                try {
                    while (!(listList.getJSONObject(ii).getString("title").equals(name)) && (ii < listList.length()))
                        ii++;
                } catch (JSONException e) {
                    //log something
                }

                if (ii != listList.length()) listList.remove(ii);
                groupsListAdapterPant = new CustomList(this, pantryList);
                groupsListViewPant.setAdapter(groupsListAdapterPant);
                groupsListAdapterList = new CustomList(this, listList);
                groupsListViewList.setAdapter(groupsListAdapterList);
                // and get whatever data you are adding
            }
        }

        if (requestCode == REQUEST_CODE_RECAP) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                boolean isList= extras.getBoolean("ISLISTASPESA");
                String name = extras.getString("NAME");
                String oldname = extras.getString("OLDNAME");
                String category = extras.getString("CATEGORY");
                int quantity = extras.getInt("QUANTITY");
                int weight=0;
                String price="";
                String expiration="";
                if(!isList) {
                     weight = extras.getInt("WEIGHT");
                     price = extras.getString("PRICE");
                     expiration = extras.getString("EXPIRATION");
                }
                String note = extras.getString("NOTE");
                if (isList) {
                    int ii = 0;
                    try {
                        while (!(listList.getJSONObject(ii).getString("title").equals(oldname)) && (ii < listList.length()))
                            ii++;
                        JSONObject nuovo = listList.getJSONObject(ii);
                        nuovo.put("title", name);
                        nuovo.put("quantity", quantity);
                        nuovo.put("description", note);

                    } catch (JSONException e) {
                        //log something
                    }
                    groupsListAdapterList = new CustomList(this, listList);
                    groupsListViewList.setAdapter(groupsListAdapterList);
                } else {
                    int ii = 0;
                    try {
                        while (!(pantryList.getJSONObject(ii).getString("title").equals(oldname)) && (ii < pantryList.length()))
                            ii++;
                        JSONObject nuovo = pantryList.getJSONObject(ii);
                        nuovo.put("title", name);
                        nuovo.put("quantity", quantity);
                        nuovo.put("weight", weight);
                        nuovo.put("price", price);
                        nuovo.put("expiration", expiration);
                        nuovo.put("description", note);

                    } catch (JSONException e) {
                        //log something
                    }
                    groupsListAdapterPant = new CustomList(this, pantryList);
                    groupsListViewPant.setAdapter(groupsListAdapterPant);
                }
            }
        }
    }

    public Intent getRecap(boolean isList, String title) {
        Intent intent = new Intent(getApplicationContext(), recapItem.class);
        intent.putExtra("ISLISTASPESA", isList);
        intent.putExtra("OLDNAME", title);
        if (isList) {
            int ii = 0;
            try {

                while (!(listList.getJSONObject(ii).getString("title").equals(title)) && (ii < listList.length())) {
                    ii++;
                }

             //   intent.putExtra("CATEGORY", listList.getJSONObject(ii).getString("category"));
                intent.putExtra("QUANTITY", listList.getJSONObject(ii).getInt("quantity"));
                intent.putExtra("NOTES", listList.getJSONObject(ii).getString("description"));

            } catch (JSONException e) {
                //log something
            }

        } else {
            int ii = 0;
            try {
                while (!(pantryList.getJSONObject(ii).getString("title").equals(title)) && (ii < pantryList.length())){
                    ii++;
                }

               // intent.putExtra("CATEGORY", pantryList.getJSONObject(ii).getString("category"));
                intent.putExtra("QUANTITY", pantryList.getJSONObject(ii).getInt("quantity"));
                intent.putExtra("WEIGHT", pantryList.getJSONObject(ii).getInt("weight"));
                intent.putExtra("PRICE", pantryList.getJSONObject(ii).getString("price"));
                intent.putExtra("EXPIRATION", pantryList.getJSONObject(ii).getString("expiration"));
                intent.putExtra("NOTES", pantryList.getJSONObject(ii).getString("description"));

            } catch (JSONException e) {
                //log something
                System.out.println("uifa");

            }
        }

        return intent;

    }


    public void removeItemPantry(boolean addToList, String name){
        int ii = 0;
        try {
            while (!(pantryList.getJSONObject(ii).getString("title").equals(name)) && (ii < pantryList.length())) {
                ii++;
            }
        }catch (JSONException e) {
            //log something
            System.out.println("uifa");
        }
        if(addToList){
            JSONObject item = new JSONObject();
            try {
                item.put("type", "product_list");
                item.put("product_type", "list");      // "list" or "pantry"
                item.put("title", name);
                item.put("quantity", pantryList.getJSONObject(ii).getInt("quantity"));
                item.put("description", pantryList.getJSONObject(ii).getString("description"));

                listList.put(item);

            } catch (JSONException e) {
                //log something
            }

            groupsListAdapterList = new CustomList(this, listList);

            groupsListViewList.setAdapter(groupsListAdapterList);
        }
        pantryList.remove(ii);

        groupsListAdapterPant = new CustomList(this, pantryList);
        groupsListViewPant.setAdapter(groupsListAdapterPant);
    }
}

