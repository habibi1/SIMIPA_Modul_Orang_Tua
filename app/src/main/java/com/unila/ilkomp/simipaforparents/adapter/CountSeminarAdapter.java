package com.unila.ilkomp.simipaforparents.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.CountSeminarRecord;

import java.util.ArrayList;
import java.util.List;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class CountSeminarAdapter extends RecyclerView.Adapter<CountSeminarAdapter.ListViewHolder>{

    Context context;
    private List<CountSeminarRecord> countSeminar = new ArrayList<>();

    public CountSeminarAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_presence_seminar_details, parent, false);
        return new ListViewHolder(view);
    }

    public void setCountSeminar(List<CountSeminarRecord> countSeminarRecords){
        if (countSeminarRecords == null)return;
        this.countSeminar.clear();
        this.countSeminar.addAll(countSeminarRecords);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        CountSeminarRecord countSeminarRecord = countSeminar.get(position);

        holder.dialogJenisSeminar.setText(countSeminarRecord.getJenis());
        holder.dialogNamaMahasiswa.setText(countSeminarRecord.getNama());
        holder.dialogNpmMahasiswa.setText(countSeminarRecord.getNPM());
        holder.dialogJudulSeminar.setText(countSeminarRecord.getJudul());
        holder.dialogWaktuSeminar.setText(countSeminarRecord.getWaktu().substring(0, 5));
        holder.dialogTanggalSeminar.setText(countSeminarRecord.getTanggal());
        holder.dialogRuangSeminar.setText(countSeminarRecord.getRuang());
        holder.dialogJumlahPeserta.setText(countSeminarRecord.getPeserta());

        holder.tvJenisSeminar.setText(countSeminarRecord.getJenis());
        holder.tvNamaMahasiswa.setText(countSeminarRecord.getNama());
        holder.tvJudulSeminar.setText(countSeminarRecord.getJudul());
        holder.tvTanggalSeminar.setText(countSeminarRecord.getTanggal());

        holder.cardViewPresenceSeminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.dialogViewDetailSeminar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.dialogViewDetailSeminar.getWindow().setGravity(Gravity.CENTER);
                holder.dialogViewDetailSeminar.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //holder.scholarshipDetail.setCanceledOnTouchOutside(false);
                holder.dialogViewDetailSeminar.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                holder.dialogViewDetailSeminar.show();
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.dialogViewDetailSeminar.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return countSeminar.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Dialog dialogViewDetailSeminar;

        TextView tvJenisSeminar, tvNamaMahasiswa, tvJudulSeminar, tvTanggalSeminar, dialogClose,
                dialogJenisSeminar, dialogNamaMahasiswa, dialogNpmMahasiswa, dialogJudulSeminar,
                dialogWaktuSeminar, dialogTanggalSeminar, dialogRuangSeminar, dialogJumlahPeserta;

        CardView cardViewPresenceSeminar;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogViewDetailSeminar = new Dialog(context);
            dialogViewDetailSeminar.setContentView(R.layout.dialog_rekapitulasi_detail_seminar);

            dialogJenisSeminar = dialogViewDetailSeminar.findViewById(R.id.tv_jenis_seminar);
            dialogNamaMahasiswa = dialogViewDetailSeminar.findViewById(R.id.tv_nama_mahasiswa);
            dialogNpmMahasiswa = dialogViewDetailSeminar.findViewById(R.id.tv_npm_mahasiswa);
            dialogJudulSeminar = dialogViewDetailSeminar.findViewById(R.id.tv_judul_seminar);
            dialogWaktuSeminar = dialogViewDetailSeminar.findViewById(R.id.tv_waktu_seminar);
            dialogTanggalSeminar = dialogViewDetailSeminar.findViewById(R.id.tv_tanggal_seminar);
            dialogRuangSeminar = dialogViewDetailSeminar.findViewById(R.id.tv_ruang_seminar);
            dialogJumlahPeserta = dialogViewDetailSeminar.findViewById(R.id.tv_jumlah_peserta_seminar);
            dialogClose = dialogViewDetailSeminar.findViewById(R.id.txtclose);

            tvJenisSeminar = itemView.findViewById(R.id.tv_jenis_seminar);
            tvNamaMahasiswa = itemView.findViewById(R.id.tv_nama_mahasiswa);
            tvJudulSeminar = itemView.findViewById(R.id.tv_judul_seminar);
            tvTanggalSeminar = itemView.findViewById(R.id.tv_tanggal_seminar);

            cardViewPresenceSeminar = itemView.findViewById(R.id.cardview_presence_seminar);
        }
    }
}
