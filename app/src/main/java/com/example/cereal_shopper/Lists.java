package com.example.cereal_shopper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class Lists extends AppCompatActivity {
    DatabaseHelper db;
    int currentGroupId;
    private Global globalApp;
    private DbGroup currentGroup;

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
        adapter.addFragment(new ListaSpesa(), "Lista Spesa");
        adapter.addFragment(new Pantry(), "Pantry");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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

            //Bundle bundle = new Bundle();
            //bundle.putInt("group_id", currentGroupId);

            switch (position){
                case 0:
                    fragment=new ListaSpesa();
                    //fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment=new Pantry();
                    //fragment.setArguments(bundle);
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






