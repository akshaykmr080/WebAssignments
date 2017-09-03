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

import com.example.akshaykumars.fbapplication.fragments.Albums;
import com.example.akshaykumars.fbapplication.fragments.Posts;
import com.example.akshaykumars.fbapplication.interfaces.OnTaskCompleted;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsTab extends AppCompatActivity implements OnTaskCompleted, Albums.OnFragmentInteractionListener, Posts.OnPostsFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapterDetails mSectionsPagerAdapter;
    private String className = "DetailsTab";
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String imageId = intent.getStringExtra(ListAdaptor.EXTRA_MESSAGE);

        String detailsUrl = "https://graph.facebook.com/v2.8/"+imageId+"? fields=id,name,picture.width(700).height(700),albums.limit(5){name,photos.limit(2){name, picture}},posts.limit(5)&access_token=EAAB4AAZA6CqQBAIDDu70EG7B370YdH3dfeZCbzLl8HSyAqfcZCsZCPDCHFNk3xUg2Nui3xIThjxtFL8X0pjJp98b7iTbjazWZBaikVKVRX7uuwIUQZAk3dBoCpKUjN5AtXLH2m9Dp5sJuOdP4MKKDA4G1LfLd1VcoZD";

        try{
            new GetHTTPData(this,className).execute(detailsUrl);
        } catch(Exception e) {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_tab, menu);
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

    @Override
    public void OnSuccess(JSONObject result) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapterDetails(getSupportFragmentManager());

        try {
            mSectionsPagerAdapter.addFragment(Albums.newInstance("Albums", result.get("albums").toString()), "Albums");
            mSectionsPagerAdapter.addFragment(Posts.newInstance("Posts", result.get("posts").toString()), "Posts");
        } catch(Exception e){
            Log.e(className,"Exception occourred while adding fragment to adaptor :"+e.getMessage());
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPostsFragmentInteraction(Uri uri) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapterDetails extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapterDetails(FragmentManager fm) {
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
