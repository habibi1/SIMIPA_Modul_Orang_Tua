package com.unila.ilkomp.simipaforparents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.unila.ilkomp.simipaforparents.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImportantDateActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String semester = "", tahunAkademik = TimeUtil.getTahunAkademik();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_date);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = findViewById(R.id.tab_important_date);
        viewPager = findViewById(R.id.view_pager);

        SetupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        if (semester.isEmpty()){
            semester = TimeUtil.getSemester();
            if (semester.equals("Ganjil")) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
            } else if (semester.equals("Genap")){
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            } else {
                Snackbar.make(this.findViewById(android.R.id.content), "Error!",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void SetupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ImportantDateGanjilFragment(), "Semester Ganjil");
        adapter.addFrag(new ImportantDateGenapFragment(), "Semester Genap");
        viewPager.setAdapter(adapter);
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
}
