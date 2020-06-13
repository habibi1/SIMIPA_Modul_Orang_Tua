package com.unila.ilkomp.simipaforparents.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.model.AchievementRecord;

import java.util.ArrayList;
import java.util.List;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ListViewHolder>{

    Context context;
    private List<AchievementRecord> listAchievement = new ArrayList<>();

    public AchievementAdapter(ArrayList<AchievementRecord> list) {
        this.listAchievement = list;
    }

    public interface OnItemClickCallback {
        void onItemClicked(AchievementRecord data);
    }

    public AchievementAdapter(Context context) {
        this.context = context;
    }

    private AchievementAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(AchievementAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public AchievementAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_achievement, parent, false);
        return new AchievementAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AchievementAdapter.ListViewHolder holder, int position) {
        AchievementRecord achievement = listAchievement.get(position);

        holder.dialogKegiatan.setText(achievement.getNamaKegiatan());
        holder.dialogPrestasi.setText(achievement.getPrestasi());
        holder.dialogTingkat.setText(achievement.getTingkat());
        holder.dialogTahun.setText(achievement.getTahunPrestasi());
        holder.dialogPenyelenggara.setText(achievement.getPenyelenggara());
        holder.dialogJabatan.setText(achievement.getJabatan());
        holder.dialogNamaMahasiswa.setText(SharedPrefManager.getNameStudentChoosed(context));
        holder.dialogNPMMahasiswa.setText(achievement.getNpm());

        Picasso.get().load(SharedPrefManager.getImageStudentChoosed(context)).into(holder.dialogProfil);

        holder.namaAcara.setText(achievement.getNamaKegiatan());
        holder.juara.setText(achievement.getPrestasi());
        holder.tingkat.setText(achievement.getTingkat());
        holder.tahun.setText(achievement.getTahunPrestasi());

        if (achievement.getPrestasi().contains("Juara I")) {
            holder.medal.setImageResource(R.drawable.ic_medal_gold);
            holder.dialogMedal.setImageResource(R.drawable.ic_medal_gold);
        } else if (achievement.getPrestasi().contains("Juara II")) {
            holder.medal.setImageResource(R.drawable.ic_medal_silver);
            holder.dialogMedal.setImageResource(R.drawable.ic_medal_silver);
        } else if (achievement.getPrestasi().contains("Juara III")){
            holder.medal.setImageResource(R.drawable.ic_medal_bronze);
            holder.dialogMedal.setImageResource(R.drawable.ic_medal_bronze);
        }

        holder.cardViewAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.dialogAchievementDetail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.dialogAchievementDetail.getWindow().setGravity(Gravity.CENTER);
                holder.dialogAchievementDetail.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //holder.scholarshipDetail.setCanceledOnTouchOutside(false);
                holder.dialogAchievementDetail.show();
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.dialogAchievementDetail.dismiss();
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listSchedule.get(holder.getAdapterPosition()));
//            }
//        });
    }

    public void setAchievement(List<AchievementRecord> achievement){
        if (achievement == null)return;
        this.listAchievement.clear();
        this.listAchievement.addAll(achievement);
    }

    @Override
    public int getItemCount() {
        return listAchievement.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Dialog dialogAchievementDetail;

        TextView namaAcara, juara, tingkat, tahun, dialogClose,
                dialogPrestasi, dialogKegiatan, dialogTingkat, dialogTahun,
                dialogPenyelenggara, dialogJabatan,
                dialogNamaMahasiswa, dialogNPMMahasiswa;
        ImageView medal, dialogMedal, dialogProfil;
        CardView cardViewAchievement;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            dialogAchievementDetail = new Dialog(context);
            dialogAchievementDetail.setContentView(R.layout.dialog_achievement_detail);

            dialogNamaMahasiswa = dialogAchievementDetail.findViewById(R.id.tv_nama_mahasiswa);
            dialogNPMMahasiswa = dialogAchievementDetail.findViewById(R.id.tv_npm_mahasiswa);
            dialogPrestasi = dialogAchievementDetail.findViewById(R.id.tv_prestasi);
            dialogKegiatan = dialogAchievementDetail.findViewById(R.id.tv_nama_kegiatan);
            dialogTingkat = dialogAchievementDetail.findViewById(R.id.tv_tingkat);
            dialogTahun = dialogAchievementDetail.findViewById(R.id.tv_tahun);
            dialogPenyelenggara = dialogAchievementDetail.findViewById(R.id.tv_penyelenggara);
            dialogJabatan = dialogAchievementDetail.findViewById(R.id.tv_jabatan);
            dialogClose = dialogAchievementDetail.findViewById(R.id.txtclose);

            dialogMedal = dialogAchievementDetail.findViewById(R.id.iv_prestasi_achievement);
            dialogProfil = dialogAchievementDetail.findViewById(R.id.image_student);

            cardViewAchievement = itemView.findViewById(R.id.cv_achievement);

            namaAcara = itemView.findViewById(R.id.tv_nama_acara);
            medal = itemView.findViewById(R.id.medal);
            juara = itemView.findViewById(R.id.tv_juara);
            tingkat = itemView.findViewById(R.id.tv_tingkat);
            tahun = itemView.findViewById(R.id.tv_tahun);
        }
    }

}
