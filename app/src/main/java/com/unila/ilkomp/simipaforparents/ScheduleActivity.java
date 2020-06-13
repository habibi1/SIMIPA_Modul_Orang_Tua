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

public class ScheduleActivity extends AppCompatActivity implements MondayScheduleFragment.OnFragmentInteractionListener, TuesdayScheduleFragment.OnFragmentInteractionListener, WednesdayScheduleFragment.OnFragmentInteractionListener, ThursdayScheduleFragment.OnFragmentInteractionListener, FridayScheduleFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_schedule);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MondayScheduleFragment(), "Senin");
        adapter.addFrag(new TuesdayScheduleFragment(), "Selasa");
        adapter.addFrag(new WednesdayScheduleFragment(), "Rabu");
        adapter.addFrag(new ThursdayScheduleFragment(), "Kamis");
        adapter.addFrag(new FridayScheduleFragment(), "Jumat");
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

/*    Toolbar toolbar;
    RecyclerView recyclerView;
    private ArrayList<ScheduleRecord> list = new ArrayList<>();
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerView = findViewById(R.id.rv_schedule);
        recyclerView.setHasFixedSize(true);

        getData();

        showRecyclerList();
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(list);
        recyclerView.setAdapter(scheduleAdapter);

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<ScheduleResponce> call = apiInterface.listSchedule("selasa", SharedPrefManager.getNPMChoosed(this), TimeUtil.getTahunAkademik(), TimeUtil.getSemester());
        call.enqueue(new Callback<ScheduleResponce>() {

            @Override
            public void onResponse(Call<ScheduleResponce> call, retrofit2.Response<ScheduleResponce> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    scheduleAdapter = new ScheduleAdapter(getApplicationContext());
                    scheduleAdapter.setSchedule(response.body().getSchedule());
                    scheduleAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(scheduleAdapter);

                    scheduleAdapter.setOnItemClickCallback(new ScheduleAdapter.OnItemClickCallback() {
                        @Override
                        public void onItemClicked(ScheduleRecord data) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponce> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("c", t.getMessage());
            }
        });
    }*/
}
