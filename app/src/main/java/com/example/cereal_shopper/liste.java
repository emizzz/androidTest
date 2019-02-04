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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class liste extends AppCompatActivity {

    ViewPager vPager;
    PageAdapter pAdapter;
    TabLayout tabLayout;
    Toolbar toolbar;

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

}
