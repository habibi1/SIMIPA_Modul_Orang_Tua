package com.unila.ilkomp.simipaforparents;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PresenceActivity extends AppCompatActivity implements PresenceKuliahFragment.OnFragmentInteractionListener, PresenceCountSeminarFragment.OnFragmentInteractionListener, PresenceBimbinganFragment.OnFragmentInteractionListener, PresenceNilaiFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_presence);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PresenceActivity.ViewPagerAdapter adapter = new PresenceActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PresenceCountSeminarFragment(), "Seminar");
        adapter.addFrag(new PresenceBimbinganFragment(), "Bimbingan");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        CardView guide, laboratory, seminar, school;

        guide = findViewById(R.id.presence_guide);
        laboratory = findViewById(R.id.presence_laboratory);
        seminar = findViewById(R.id.presence_seminar);
        school = findViewById(R.id.presence_school);

        guide.setOnClickListener(this);
        laboratory.setOnClickListener(this);
        seminar.setOnClickListener(this);
        school.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.presence_guide:
                Intent intentPresenceGuide = new Intent(this, PresenceGuideActivity.class);
                startActivity(intentPresenceGuide);
                break;
            case R.id.presence_laboratory:
                Intent intentLaboratory = new Intent(this, PresenceLaboratoryActivity.class);
                startActivity(intentLaboratory);
                break;
            case R.id.presence_seminar:
                Intent intentSeminar = new Intent(this, PresenceSeminarActivity.class);
                startActivity(intentSeminar);
                break;
            case R.id.presence_school:
                Intent intentSchool = new Intent(this, PresenceSchoolActivity.class);
                startActivity(intentSchool);
                break;
        }
    }*/
}
