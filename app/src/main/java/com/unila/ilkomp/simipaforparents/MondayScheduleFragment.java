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

import com.unila.ilkomp.simipaforparents.adapter.ScheduleAdapter;
import com.unila.ilkomp.simipaforparents.model.ScheduleResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.util.TimeUtil;

import retrofit2.Call;
import retrofit2.Callback;


public class MondayScheduleFragment extends Fragment {

    RecyclerView recyclerView;
    ScheduleAdapter scheduleAdapter;
    ProgressBar progressBar;
    TextView dataEmpty;
    View helpColor;

    private OnFragmentInteractionListener mListener;

    public MondayScheduleFragment() {
        // Required empty public constructor
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

        View root = inflater.inflate(R.layout.fragment_monday_schedule, container, false);

        helpColor = root.findViewById(R.id.help_color);
        recyclerView = root.findViewById(R.id.rv_schedule);
        progressBar = root.findViewById(R.id.progressBar);
        dataEmpty = root.findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
/*
        progressBar.setVisibility(View.GONE);
        ScheduleRecord scheduleRecord1 = new ScheduleRecord("Mobile Lanjut", "GIK L1C", "Ardiansyah, S.Kom., M.Kom.", "07:30:00", "09:10:00", "praktikum");
        ScheduleRecord scheduleRecord2 = new ScheduleRecord("Matematika Diskrit", "GIK L1A", "Dr. Eng. Admi Syarif", "09:20:00", "11:00:00", "teori");
        ScheduleRecord scheduleRecord3 = new ScheduleRecord("Rekayasa Perangkat Lunak", "GIK L2", "Astria Hijriani, S.Kom., M.Kom.", "11:10:00", "12:50:00", "praktikum");
        ScheduleRecord scheduleRecord4 = new ScheduleRecord("Sistem Operasi", "GIK L1C", "Dwi Sakethi, M.Kom.", "13:30:00", "15:10:00", "teori");
        ScheduleRecord scheduleRecord5 = new ScheduleRecord("Pengantar Sistem informasi", "GIK L1C", "Anie Rose Irawati, S.T., M.Cs.", "15:20:00", "17:00:00", "teori");
        List<ScheduleRecord> coba = new ArrayList<>();
        coba.add(scheduleRecord1);
        coba.add(scheduleRecord2);
        coba.add(scheduleRecord3);
        coba.add(scheduleRecord4);
        coba.add(scheduleRecord5);
        scheduleAdapter = new ScheduleAdapter(getContext());
        scheduleAdapter.setSchedule(coba);
        scheduleAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(scheduleAdapter);
*/
        getData();

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getData() {

        helpColor.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        dataEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<ScheduleResponce> call = apiInterface.listSchedule("senin", SharedPrefManager.getNPMChoosed(getContext()), TimeUtil.getTahunAkademik(), TimeUtil.getSemester());
        call.enqueue(new Callback<ScheduleResponce>() {

            @Override
            public void onResponse(Call<ScheduleResponce> call, retrofit2.Response<ScheduleResponce> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0){
                        helpColor.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        dataEmpty.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                        scheduleAdapter = new ScheduleAdapter(getContext());
                        scheduleAdapter.setSchedule(response.body().getSchedule());
                        scheduleAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(scheduleAdapter);
                    } else {
                        helpColor.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        dataEmpty.setVisibility(View.VISIBLE);

                        if (isAdded())
                            dataEmpty.setText(getString(R.string.data_kosong));

                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    helpColor.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    dataEmpty.setVisibility(View.VISIBLE);

                    if (isAdded())
                        dataEmpty.setText(getString(R.string.terjadi_kesalahan));

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponce> call, Throwable t) {
                helpColor.setVisibility(View.GONE);
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
