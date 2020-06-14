package com.unila.ilkomp.simipaforparents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.CountSeminarAdapter;
import com.unila.ilkomp.simipaforparents.model.CountSeminarRecord;
import com.unila.ilkomp.simipaforparents.model.CountSeminarResponce;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class PresenceCountSeminarFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    CountSeminarAdapter countSeminarAdapter;
    private List<CountSeminarRecord> countSeminar = new ArrayList<>();
    TextView tvJumlahSeminarKP, tvJumlahSeminarUsul, tvJumlahSeminarHasil, tvDaftarSeminar;

    CardView cvSeminarKp, cvSeminarUsul, cvSeminarHasil;
    ProgressBar progressBar;
    TextView tv_empty_data;

    boolean clickSeminarKP = false,
            clickSeminarUsul = false,
            clickSeminarHasil = false,
            clickConfirm = false;

    View viewCountSeminar;

    List<CountSeminarRecord> seminarKP = new ArrayList<>();
    List<CountSeminarRecord> seminarUsul = new ArrayList<>();
    List<CountSeminarRecord> seminarHasil = new ArrayList<>();
    List<CountSeminarRecord> seminarAll = new ArrayList<>();

    private PresenceCountSeminarFragment.OnFragmentInteractionListener mListener;

    public PresenceCountSeminarFragment() {
        // Required empty public constructor
    }

    public static PresenceCountSeminarFragment newInstance(String param1, String param2) {
        PresenceCountSeminarFragment fragment = new PresenceCountSeminarFragment();
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

        View root = inflater.inflate(R.layout.fragment_presence_count_seminar, container, false);

        tvJumlahSeminarKP = root.findViewById(R.id.tv_jumlah_seminar_kp);
        tvJumlahSeminarUsul = root.findViewById(R.id.tv_jumlah_seminar_usul);
        tvJumlahSeminarHasil = root.findViewById(R.id.tv_jumlah_seminar_hasil);
        tvDaftarSeminar = root.findViewById(R.id.tv_daftar_seminar);

        recyclerView = root.findViewById(R.id.rv_seminar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = root.findViewById(R.id.progressbar);
        tv_empty_data = root.findViewById(R.id.data_empty);

        viewCountSeminar = root.findViewById(R.id.view_count_seminar);

        cvSeminarKp = root.findViewById(R.id.cv_seminar_kp);
        cvSeminarUsul = root.findViewById(R.id.cv_seminar_usul);
        cvSeminarHasil = root.findViewById(R.id.cv_seminar_hasil);

        countSeminarAdapter = new CountSeminarAdapter(getContext());

        getData();

        return root;
    }

    @Override
    public void onClick(View v) {

        elapseClick();

        switch (v.getId()){
            case R.id.cv_seminar_kp:
                if (clickSeminarKP){

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_unclick));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_unclick));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_unclick));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_semua_seminar));

                    clickSeminarKP = false;
                    clickSeminarUsul = false;
                    clickSeminarHasil = false;

                    recyclerView.setVisibility(View.VISIBLE);
                    countSeminarAdapter.setCountSeminar(seminarAll);
                    countSeminarAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(countSeminarAdapter);
                } else {

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_click));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_unclick));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_unclick));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_seminar_kp));

                    clickSeminarKP = true;
                    clickSeminarUsul = false;
                    clickSeminarHasil = false;

                    if (seminarKP == null) {
                        recyclerView.setVisibility(View.GONE);
                        tv_empty_data.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        countSeminarAdapter.setCountSeminar(seminarKP);
                        countSeminarAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(countSeminarAdapter);
                    }

                }
                break;
            case R.id.cv_seminar_usul:
                if (clickSeminarUsul) {

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_unclick));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_unclick));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_unclick));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_semua_seminar));

                    clickSeminarKP = false;
                    clickSeminarUsul = false;
                    clickSeminarHasil = false;

                    recyclerView.setVisibility(View.VISIBLE);
                    countSeminarAdapter.setCountSeminar(seminarAll);
                    countSeminarAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(countSeminarAdapter);
                } else {

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_unclick));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_click));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_unclick));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_seminar_usul));

                    clickSeminarKP = false;
                    clickSeminarUsul = true;
                    clickSeminarHasil = false;

                    if (seminarUsul == null) {
                        recyclerView.setVisibility(View.GONE);
                        tv_empty_data.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        countSeminarAdapter.setCountSeminar(seminarUsul);
                        countSeminarAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(countSeminarAdapter);
                    }
                }
                break;
            case R.id.cv_seminar_hasil:
                if (clickSeminarHasil) {

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_unclick));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_unclick));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_unclick));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_semua_seminar));

                    clickSeminarKP = false;
                    clickSeminarUsul = false;
                    clickSeminarHasil = false;

                    recyclerView.setVisibility(View.VISIBLE);
                    countSeminarAdapter.setCountSeminar(seminarAll);
                    countSeminarAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(countSeminarAdapter);
                } else {

                    cvSeminarKp.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_kp_unclick));
                    cvSeminarUsul.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_usul_unclick));
                    cvSeminarHasil.setCardBackgroundColor(getResources().getColor(R.color.color_seminar_hasil_click));

                    if (isAdded())
                        tvDaftarSeminar.setText(getResources().getString(R.string.daftar_seminar_hasil));

                    clickSeminarKP = true;
                    clickSeminarUsul = false;
                    clickSeminarHasil = true;

                    if (seminarHasil == null) {
                        recyclerView.setVisibility(View.GONE);
                        tv_empty_data.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        countSeminarAdapter.setCountSeminar(seminarHasil);
                        countSeminarAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(countSeminarAdapter);
                    }
                }
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PresenceCountSeminarFragment.OnFragmentInteractionListener) {
            mListener = (PresenceCountSeminarFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getData() {

        tvDaftarSeminar.setVisibility(View.GONE);
        viewCountSeminar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tv_empty_data.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<CountSeminarResponce> call = apiInterface.countSeminar(SharedPrefManager.getNPMChoosed(getContext()));
        call.enqueue(new Callback<CountSeminarResponce>() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<CountSeminarResponce> call, retrofit2.Response<CountSeminarResponce> response) {

                tvDaftarSeminar.setVisibility(View.VISIBLE);
                viewCountSeminar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                tv_empty_data.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0) {

                        if (isAdded()) {
                            tvDaftarSeminar.setText(getString(R.string.daftar_semua_seminar));
                            tvJumlahSeminarKP.setText(String.format("%d", response.body().getJumlahSeminarKP()));
                            tvJumlahSeminarUsul.setText(String.format("%d", response.body().getJumlahSeminarUsul()));
                            tvJumlahSeminarHasil.setText(String.format("%d", response.body().getJumlahSeminarHasil()));
                        }

                        for (CountSeminarRecord seminarRecord : response.body().getSeminarRecords()) {

                            seminarAll.add(seminarRecord);

                            if (seminarRecord.getJenis().toLowerCase().contains("seminar kerja"))
                                seminarKP.add(seminarRecord);
                            else if (seminarRecord.getJenis().toLowerCase().contains("seminar usul"))
                                seminarUsul.add(seminarRecord);
                            else if (seminarRecord.getJenis().toLowerCase().contains("seminar hasil"))
                                seminarHasil.add(seminarRecord);
                        }

                        clickConfirm = true;
                        setClick(clickConfirm);

                        countSeminarAdapter.setCountSeminar(seminarAll);
                        countSeminarAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(countSeminarAdapter);

                    } else {

                        if (isAdded()) {
                            tvDaftarSeminar.setText(getString(R.string.daftar_semua_seminar));
                            tvJumlahSeminarKP.setText(getString(R.string.nol));
                            tvJumlahSeminarUsul.setText(getString(R.string.nol));
                            tvJumlahSeminarHasil.setText(getString(R.string.nol));
                        }

                        clickConfirm = false;
                        setClick(clickConfirm);

                        recyclerView.setVisibility(View.GONE);
                        tv_empty_data.setVisibility(View.VISIBLE);

                        if (isAdded())
                            tv_empty_data.setText(getString(R.string.data_kosong));

                        progressBar.setVisibility(View.GONE);

                    }

                } else {

                    tvDaftarSeminar.setVisibility(View.GONE);
                    viewCountSeminar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    tv_empty_data.setVisibility(View.VISIBLE);

                    if (isAdded())
                        tv_empty_data.setText(getString(R.string.terjadi_kesalahan));

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CountSeminarResponce> call, Throwable t) {
                tvDaftarSeminar.setVisibility(View.GONE);
                viewCountSeminar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                tv_empty_data.setVisibility(View.VISIBLE);

                if (isAdded())
                    tv_empty_data.setText(getString(R.string.server_error));

                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }

    private void setClick(boolean clickConfirm){
        if (clickConfirm){
            cvSeminarKp.setOnClickListener(this);
            cvSeminarUsul.setOnClickListener(this);
            cvSeminarHasil.setOnClickListener(this);
        } else {
            cvSeminarKp.setClickable(false);
            cvSeminarUsul.setClickable(false);
            cvSeminarHasil.setClickable(false);
        }
    }
}
