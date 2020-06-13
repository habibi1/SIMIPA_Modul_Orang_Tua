package com.unila.ilkomp.simipaforparents;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.AchievementAdapter;
import com.unila.ilkomp.simipaforparents.model.AchievementResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AchievementAcademicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementAcademicFragment extends Fragment {

    RecyclerView recyclerView;
    AchievementAdapter achievementAdapter;
    ProgressBar progressBar;
    TextView dataEmpty;

    private AchievementAcademicFragment.OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public AchievementAcademicFragment() {
        // Required empty public constructor
    }

    public static AchievementAcademicFragment newInstance(String param1, String param2) {
        AchievementAcademicFragment fragment = new AchievementAcademicFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_achievement_academic, container, false);

        recyclerView = root.findViewById(R.id.rv_achievement);
        progressBar = root.findViewById(R.id.progressBar);
        dataEmpty = root.findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        return root;
    }

    private void getData() {
        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<AchievementResponce> call = apiInterface.listAchievement(SharedPrefManager.getNPMChoosed(getContext()), "akademik");
        call.enqueue(new Callback<AchievementResponce>() {

            @Override
            public void onResponse(Call<AchievementResponce> call, retrofit2.Response<AchievementResponce> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0){
                        achievementAdapter = new AchievementAdapter(getContext());
                        achievementAdapter.setAchievement(response.body().getAchievemnts());
                        achievementAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(achievementAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);
                        dataEmpty.setText(getString(R.string.data_kosong));
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);
                    dataEmpty.setText(getString(R.string.terjadi_kesalahan));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AchievementResponce> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);
                dataEmpty.setText(getString(R.string.server_error));
                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }
}
