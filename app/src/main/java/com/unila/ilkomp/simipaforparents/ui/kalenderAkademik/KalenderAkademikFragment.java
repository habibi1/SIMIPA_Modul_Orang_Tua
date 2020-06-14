package com.unila.ilkomp.simipaforparents.ui.kalenderAkademik;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.unila.ilkomp.simipaforparents.KalenderAkademikGanjilFragment;
import com.unila.ilkomp.simipaforparents.KalenderAkademikGenapFragment;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademikRecord;
import com.unila.ilkomp.simipaforparents.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class KalenderAkademikFragment extends Fragment implements KalenderAkademikGanjilFragment.OnFragmentInteractionListener, KalenderAkademikGenapFragment.OnFragmentInteractionListener{

    private String semester = "", tahunAkademik = TimeUtil.getTahunAkademik();
    ProgressBar progressBar;
    TextView dataEmpty;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentActivity myContext;

    private ArrayList<KalenderAkademikRecord> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_kalender_akademik, container, false);

        progressBar = root.findViewById(R.id.progressBar);
        dataEmpty = root.findViewById(R.id.data_empty);
        viewPager = root.findViewById(R.id.view_pager_kalender_akademik);
        setupViewPager(viewPager);

        tabLayout = root.findViewById(R.id.tab_kalender_akademik);
        tabLayout.setupWithViewPager(viewPager);

        if (semester.isEmpty()){
            semester = TimeUtil.getSemester();
            if (semester.equals("Ganjil")) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
            } else if (semester.equals("Genap")) {
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Error!",
                        Snackbar.LENGTH_SHORT).show();
            }
        }

        TextView TA = root.findViewById(R.id.tahunAjaran);

        if (isAdded())
            TA.setText(getString(R.string.title_kalender_akademik) + tahunAkademik);

        return root;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new KalenderAkademikGanjilFragment(), "Semester Ganjil");
        adapter.addFrag(new KalenderAkademikGenapFragment(), "Semester Genap");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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