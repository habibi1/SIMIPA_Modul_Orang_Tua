package com.unila.ilkomp.simipaforparents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.unila.ilkomp.simipaforparents.model.PresenceBimbinganRecord;
import com.unila.ilkomp.simipaforparents.model.PresenceBimbinganResponce;
import com.unila.ilkomp.simipaforparents.model.PresenceSchool;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class PresenceBimbinganFragment extends Fragment {

    ImageView ivCircleUsul, ivCircleHasil, ivCircleKompre, dialogFotoMahasiswa;
    TextView tvDateUsul, tvDateHasil, tvDateKompre, tvDataEmpty, dialogClose,
            dialogNamaMahasiswa, dialogNpmMahasiswa, dialogStatus, dialogJenisSeminar,
            dialogJudulSeminar, dialogWaktuSeminar, dialogTanggalSeminar, dialogRuangSeminar;

    Dialog dialogViewDetailBimbingan;

    View viewBimbingan, viewDetailUsul, viewDetailHasil, viewDetailKompre;

    CardView cvUsul, cvHasil, cvKompre;

    ProgressBar progressBar;

    private ArrayList<PresenceSchool> list = new ArrayList<>();

    private PresenceBimbinganFragment.OnFragmentInteractionListener mListener;

    public PresenceBimbinganFragment() {
        // Required empty public constructor
    }

    public static PresenceBimbinganFragment newInstance(String param1, String param2) {
        PresenceBimbinganFragment fragment = new PresenceBimbinganFragment();
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

        View root = inflater.inflate(R.layout.fragment_presence_bimbingan, container, false);

        dialogViewDetailBimbingan = new Dialog(getContext());
        dialogViewDetailBimbingan.setContentView(R.layout.dialog_rekapitulasi_detail_bimbingan);

        dialogNamaMahasiswa = dialogViewDetailBimbingan.findViewById(R.id.tv_nama_mahasiswa);
        dialogNpmMahasiswa = dialogViewDetailBimbingan.findViewById(R.id.tv_npm_mahasiswa);
        dialogStatus = dialogViewDetailBimbingan.findViewById(R.id.tv_status);
        dialogJenisSeminar = dialogViewDetailBimbingan.findViewById(R.id.tv_jenis_seminar);
        dialogWaktuSeminar = dialogViewDetailBimbingan.findViewById(R.id.tv_waktu_seminar);
        dialogTanggalSeminar = dialogViewDetailBimbingan.findViewById(R.id.tv_tanggal_seminar);
        dialogRuangSeminar = dialogViewDetailBimbingan.findViewById(R.id.tv_ruang_seminar);
        dialogJudulSeminar = dialogViewDetailBimbingan.findViewById(R.id.tv_judul_seminar);
        dialogClose = dialogViewDetailBimbingan.findViewById(R.id.txtclose);

        dialogFotoMahasiswa = dialogViewDetailBimbingan.findViewById(R.id.image_student);

        ivCircleUsul = root.findViewById(R.id.circle_top);
        ivCircleHasil = root.findViewById(R.id.circle_mid);
        ivCircleKompre = root.findViewById(R.id.circle_bot);

        tvDateUsul = root.findViewById(R.id.tv_date_usul);
        tvDateHasil = root.findViewById(R.id.tv_date_hasil);
        tvDateKompre = root.findViewById(R.id.tv_date_kompre);
        tvDataEmpty = root.findViewById(R.id.data_empty);

        cvUsul = root.findViewById(R.id.item_cd_usul);
        cvHasil = root.findViewById(R.id.item_cd_hasil);
        cvKompre = root.findViewById(R.id.item_cd_kompre);

        tvDateUsul.setText("-");
        tvDateHasil.setText("-");
        tvDateKompre.setText("-");

        progressBar = root.findViewById(R.id.progressBar);

        viewBimbingan = root.findViewById(R.id.layoutBimbingan);
        viewDetailUsul = root.findViewById(R.id.view_detail_usul);
        viewDetailHasil = root.findViewById(R.id.view_detail_hasil);
        viewDetailKompre = root.findViewById(R.id.view_detail_kompre);

        viewDetailUsul.setVisibility(View.GONE);
        viewDetailHasil.setVisibility(View.GONE);
        viewDetailKompre.setVisibility(View.GONE);

/*        recyclerView = root.findViewById(R.id.rv_presence_bimbingan);
        //progressBar = root.findViewById(R.id.progressBar);
        //dataEmpty = root.findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list.clear();
        list.addAll(PresenceSchoolData.getListData());
        showRecyclerList();*/

        getData();

        return root;
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
        if (context instanceof PresenceBimbinganFragment.OnFragmentInteractionListener) {
            mListener = (PresenceBimbinganFragment.OnFragmentInteractionListener) context;
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

        progressBar.setVisibility(View.VISIBLE);
        viewBimbingan.setVisibility(View.GONE);
        tvDataEmpty.setVisibility(View.GONE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<PresenceBimbinganResponce> call = apiInterface.rekapitulasiBimbinganSkripsi(SharedPrefManager.getNPMChoosed(getContext()));
        call.enqueue(new Callback<PresenceBimbinganResponce>() {

            @Override
            public void onResponse(Call<PresenceBimbinganResponce> call, retrofit2.Response<PresenceBimbinganResponce> response) {

                progressBar.setVisibility(View.GONE);
                viewBimbingan.setVisibility(View.VISIBLE);
                tvDataEmpty.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getTotalRecords() > 0){
                        for (PresenceBimbinganRecord presenceBimbinganRecord : response.body().getRecords()){

                            if (presenceBimbinganRecord.getJenisSeminar().toLowerCase().contains("seminar usul")){
                                tvDateUsul.setText(presenceBimbinganRecord.getTanggal());
                                ivCircleUsul.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_green_24dp));
                                cvUsul.setClickable(true);
                                viewDetailUsul.setVisibility(View.VISIBLE);

                                cvUsul.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        elapseClick();
                                        showDialogDetail(presenceBimbinganRecord);
                                    }
                                });
                            }

                            if (presenceBimbinganRecord.getJenisSeminar().toLowerCase().contains("seminar hasil")){
                                tvDateHasil.setText(presenceBimbinganRecord.getTanggal());
                                ivCircleHasil.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_green_24dp));
                                cvHasil.setClickable(true);
                                viewDetailHasil.setVisibility(View.VISIBLE);

                                cvHasil.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        elapseClick();
                                        showDialogDetail(presenceBimbinganRecord);
                                    }
                                });
                            }

                            if (presenceBimbinganRecord.getJenisSeminar().toLowerCase().contains("seminar kompre")){
                                tvDateKompre.setText(presenceBimbinganRecord.getTanggal());
                                ivCircleKompre.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_green_24dp));
                                cvKompre.setClickable(true);
                                viewDetailKompre.setVisibility(View.VISIBLE);

                                cvKompre.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        elapseClick();
                                        showDialogDetail(presenceBimbinganRecord);
                                    }
                                });
                            }
                        }
                    }

                } else {

                    progressBar.setVisibility(View.GONE);
                    tvDataEmpty.setVisibility(View.VISIBLE);

                    if (isAdded())
                        tvDataEmpty.setText(getString(R.string.terjadi_kesalahan));

                    viewBimbingan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PresenceBimbinganResponce> call, Throwable t) {
                viewBimbingan.setVisibility(View.GONE);
                tvDataEmpty.setVisibility(View.VISIBLE);

                if (isAdded())
                    tvDataEmpty.setText(getString(R.string.server_error));

                progressBar.setVisibility(View.GONE);
                Log.d("c", t.getMessage());
            }
        });
    }

    private void showDialogDetail(PresenceBimbinganRecord presenceBimbinganRecord) {

        dialogNamaMahasiswa.setText(SharedPrefManager.getNameStudentChoosed(getContext()));
        dialogNpmMahasiswa.setText(SharedPrefManager.getNPMChoosed(getContext()));
        dialogJenisSeminar.setText(presenceBimbinganRecord.getJenisSeminar());
        dialogJudulSeminar.setText(presenceBimbinganRecord.getJudul());
        dialogWaktuSeminar.setText(presenceBimbinganRecord.getWaktu());
        dialogTanggalSeminar.setText(presenceBimbinganRecord.getTanggal());
        dialogRuangSeminar.setText(presenceBimbinganRecord.getRuangan());

        Picasso.get().load(SharedPrefManager.getImageStudentChoosed(getContext())).into(dialogFotoMahasiswa);

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                dialogViewDetailBimbingan.dismiss();
            }
        });

        dialogViewDetailBimbingan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogViewDetailBimbingan.getWindow().setGravity(Gravity.CENTER);
        dialogViewDetailBimbingan.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogViewDetailBimbingan.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        dialogViewDetailBimbingan.show();

    }

}
