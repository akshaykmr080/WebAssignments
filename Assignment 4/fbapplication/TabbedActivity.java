package com.example.akshaykumars.fbapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.akshaykumars.fbapplication.fragments.Events;
import com.example.akshaykumars.fbapplication.fragments.Groups;
import com.example.akshaykumars.fbapplication.fragments.Pages;
import com.example.akshaykumars.fbapplication.fragments.Places;
import com.example.akshaykumars.fbapplication.fragments.Users;
import com.example.akshaykumars.fbapplication.interfaces.OnTaskCompleted;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabbedActivity extends AppCompatActivity implements OnTaskCompleted,Places.OnPlacesFragmentInteractionListener, Events.OnEventsFragmentInteractionListener,Groups.OnGroupsFragmentInteractionListener, Users.OnFragmentInteractionListener, Pages.OnPagesFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final String className = "TabbedActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String url = "http://sample-env.meq2yuf6r3.us-west-2.elasticbeanstalk.com/index.php?searchValue="+message;

        try{
            new GetHTTPData(this,className).execute(url);
        } catch(Exception e) {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnSuccess(JSONObject result) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        try {
            mSectionsPagerAdapter.addFragment(Users.newInstance("Users", result.get("users").toString()), "Users");
            mSectionsPagerAdapter.addFragment(Pages.newInstance("Pages", result.get("pages").toString()), "Pages");
            mSectionsPagerAdapter.addFragment(Events.newInstance("Events", result.get("events").toString()), "Events");
            mSectionsPagerAdapter.addFragment(Places.newInstance("Places", result.get("places").toString()), "Places");
            mSectionsPagerAdapter.addFragment(Groups.newInstance("Groups", result.get("groups").toString()), "Groups");
            //mSectionsPagerAdapter.addFragment(Favourites.newInstance("Favourites", null), "Favourites Tab");
        } catch(Exception e){
            Log.e(className,"Exception occourred while adding fragment to adaptor :"+e.getMessage());
        }
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_users);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_users);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_events);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_places);
        tabLayout.getTabAt(4).setIcon(R.mipmap.ic_groups);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPagesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGroupsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPlacesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onEventsFragmentInteraction(Uri uri) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
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
