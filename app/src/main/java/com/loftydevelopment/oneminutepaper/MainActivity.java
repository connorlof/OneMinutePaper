package com.loftydevelopment.oneminutepaper;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.loftydevelopment.oneminutepaper.model.Paper;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PaperHistoryFragment tab2;
    WritePaperFragment tab1;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int defaultValue = 0;
        int page = getIntent().getIntExtra("One", defaultValue);
        mViewPager.setCurrentItem(page);

        try{
            migrateOldDatabase();
        }catch (Exception e){
            //do nothing, no old database records to migrate
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    tab1 = new WritePaperFragment();
                    return tab1;
                case 1:
                    tab2 = new PaperHistoryFragment();
                    return tab2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "New Paper";
                case 1:
                    return "Archive";
            }
            return null;
        }
    }

    private void migrateOldDatabase() {
        ArrayList<Paper> paperList = new ArrayList<>();

        PaperDatabase paperDatabaseRoom = PaperDatabase.getInstance(this);
        SQLiteDatabase paperDatabase = this.openOrCreateDatabase("Papers", MODE_PRIVATE, null);
        paperDatabase.execSQL("CREATE TABLE IF NOT EXISTS papers (subject VARCHAR, mainideas VARCHAR, questions VARCHAR, id INTEGER PRIMARY KEY)");

        Cursor c = paperDatabase.rawQuery("SELECT * FROM papers", null);

        int subjectIndex = c.getColumnIndex("subject");
        int mainideasIndex = c.getColumnIndex("mainideas");
        int questionsIndex = c.getColumnIndex("questions");

        if(c.moveToLast()){
            do {
                paperList.add(new Paper(c.getString(subjectIndex), c.getString(mainideasIndex), c.getString(questionsIndex)));
            } while(c.moveToPrevious());
        }

        //Check if any papers exist in the old database
        if(paperList.size() > 0) {
            for(Paper paper : paperList) {
                paperDatabaseRoom.paperDao().insertPaper(paper);
            }

            paperDatabase.execSQL("DELETE FROM papers");
        }

    }

}
