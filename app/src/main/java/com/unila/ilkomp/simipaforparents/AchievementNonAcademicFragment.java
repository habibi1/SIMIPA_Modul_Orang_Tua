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
import retrofit2.internal.EverythingIsNonNull;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AchievementNonAcademicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementNonAcademicFragment extends Fragment {

    RecyclerView recyclerView;
    AchievementAdapter achievementAdapter;
    ProgressBar progressBar;
    TextView dataEmpty;

    private AchievementNonAcademicFragment.OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public AchievementNonAcademicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementNonAcademicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementNonAcademicFragment newInstance(String param1, String param2) {
        AchievementNonAcademicFragment fragment = new AchievementNonAcademicFragment();
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

        View root = inflater.inflate(R.layout.fragment_achievement_non_academic, container, false);

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

        Call<AchievementResponce> call = apiInterface.listAchievement(SharedPrefManager.getNPMChoosed(getContext()), "non akademik");
        call.enqueue(new Callback<AchievementResponce>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<AchievementResponce> call, retrofit2.Response<AchievementResponce> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    {
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
