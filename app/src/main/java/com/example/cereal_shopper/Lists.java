package com.example.cereal_shopper;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*
*Activity with two fragments for the pantry and shopping list
**/
public class Lists extends AppCompatActivity {
    DatabaseHelper db;
    int currentGroupId;
    private Global globalApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        //set the group id in the Global class
        globalApp = (Global)getApplicationContext();
        currentGroupId = getIntent().getIntExtra("GROUP_ID", globalApp.getCurrentGroupId());
        globalApp.setCurrentGroupId(currentGroupId);

        db = new DatabaseHelper(getApplicationContext());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new ShoppingList(), getString(R.string.lista_spesa));
        adapter.addFragment(new Pantry(), getString(R.string.pantry));
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (getIntent().hasExtra("tab_index")) {
            try{
                int index = getIntent().getExtras().getInt("tab_index");
                Log.d("----", Integer.toString(index));
                tabLayout.getTabAt(index).select();
            }
            catch (Exception e){
                Log.d("Lists", "tab_index not selected");
            }

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(db.getGroup(currentGroupId).getTitle());
        toolbar.setNavigationIcon(R.drawable.left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);            }
        });
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class PageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment=null;

            switch (position){
                case 0:
                    fragment=new ShoppingList();
                    break;
                case 1:
                    fragment=new Pantry();
                    break;
                default:
                    fragment=null;
                    break;
            }
            return  fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}






