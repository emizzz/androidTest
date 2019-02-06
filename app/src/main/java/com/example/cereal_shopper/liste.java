package com.example.cereal_shopper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
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

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.rsz_1rsz_icons8_back_arrow_480);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        vPager = (ViewPager) findViewById(R.id.pager);
        pAdapter =new PageAdapter(getSupportFragmentManager());
        vPager.setAdapter(pAdapter);

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vPager);

        JSONObject item111 = new JSONObject();
        JSONObject item222 = new JSONObject();
        JSONObject item333 = new JSONObject();

        try{
            item111.put("type", "product_list");
            item111.put("title", "Caffe");
            item111.put("description", "mocha");
            item111.put("product_type", "pantry");      // "list" or "pantry"
            item222.put("type", "product_list");
            item222.put("title", "Pasta");
            item222.put("description", "Spaghetti");
            item222.put("product_type", "pantry");      // "list" or "pantry"

            item333.put("type", "product_list");
            item333.put("title", "Latte");
            item333.put("description", "parzialmente scremato");
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
            switch(i){
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

                groupsListAdapterList = new List(this, listList);

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
                    item.put("description", note);

                    pantryList.put(item);

                } catch (JSONException e) {
                    //log something
                }
                int ii=0;
                try {
                while(!(listList.getJSONObject(ii).getString("title").equals(name))&&(ii<listList.length()))
                    ii++;
                } catch (JSONException e) {
                    //log something
                }

                if(ii!=listList.length()) listList.remove(ii);
                groupsListAdapterPant = new List(this, pantryList);
                groupsListViewPant.setAdapter(groupsListAdapterPant);
                groupsListAdapterList = new List(this, listList);
                groupsListViewList.setAdapter(groupsListAdapterList);
                // and get whatever data you are adding
            }
        }
    }
}
