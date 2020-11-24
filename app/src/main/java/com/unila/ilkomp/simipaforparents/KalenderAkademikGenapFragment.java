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

import com.unila.ilkomp.simipaforparents.adapter.KalenderAkademikAdapter;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademikResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.util.TimeUtil;

import retrofit2.Call;
import retrofit2.Callback;

public class KalenderAkademikGenapFragment extends Fragment {

    RecyclerView recyclerView;
    KalenderAkademikAdapter kalenderAkademikAdapter;
    ProgressBar progressBar;
    TextView dataEmpty;
    String semester = "genap", tahunAkademik = TimeUtil.getTahunAkademik();

    public KalenderAkademikGenapFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KalenderAkademikGenapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KalenderAkademikGenapFragment newInstance(String param1, String param2) {
        KalenderAkademikGenapFragment fragment = new KalenderAkademikGenapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kalender_akademik_genap, container, false);

        recyclerView = root.findViewById(R.id.rv_kalender_akademik);
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

        Call<KalenderAkademikResponce> kalenderAkademik = apiInterface.kalenderAkademik(semester, tahunAkademik);
        kalenderAkademik.enqueue(new Callback<KalenderAkademikResponce>() {

            @Override
            public void onResponse(Call<KalenderAkademikResponce> call, retrofit2.Response<KalenderAkademikResponce> response) {

                recyclerView.setVisibility(View.VISIBLE);
                dataEmpty.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body().getTotalRecords() > 0) {
                        kalenderAkademikAdapter = new KalenderAkademikAdapter(getActivity().getApplicationContext());
                        kalenderAkademikAdapter.setListKalenderAkademik(response.body().getKalenderAkademikRecords());
                        kalenderAkademikAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(kalenderAkademikAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);

                        if (isAdded())
                            dataEmpty.setText(getString(R.string.data_kosong));

                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);

                    if (isAdded())
                        dataEmpty.setText(getString(R.string.terjadi_kesalahan));

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<KalenderAkademikResponce> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                dataEmpty.setVisibility(View.VISIBLE);

                if (isAdded())
                    dataEmpty.setText(getString(R.string.server_error));

                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }
}
