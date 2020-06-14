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
import com.unila.ilkomp.simipaforparents.model.ScholarshipRecord;

import java.util.ArrayList;
import java.util.List;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.ListViewHolder> {
    Context context;
    private List<ScholarshipRecord> listScholarship = new ArrayList<>();
    private OnItemClickCallback listener;

    public ScholarshipAdapter(ArrayList<ScholarshipRecord> list) {
        this.listScholarship = list;
    }

    public interface OnItemClickCallback {
        void onItemClicked(ScholarshipRecord data);
    }

    public ScholarshipAdapter(Context context) {
        this.context = context;
    }

    private ScholarshipAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ScholarshipAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ScholarshipAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_scholarship, parent, false);
        return new ScholarshipAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScholarshipAdapter.ListViewHolder holder, int position) {
        ScholarshipRecord scholarshipRecord = listScholarship.get(position);
        holder.status.setText(scholarshipRecord.getStatusAjukan());
        holder.nameScholarship.setText(scholarshipRecord.getNamaBeasiswa());
        holder.semester.setText("Semester " + scholarshipRecord.getSemester());
        holder.yearScholarship.setText(scholarshipRecord.getTahunBeasiswa());

        if (scholarshipRecord.getStatusAjukan().trim().equals("Berhasil")){
            holder.imageView_dialog_status.setImageResource(R.drawable.ic_check_circle_green_24dp);
            holder.imageView_status.setImageResource(R.drawable.ic_check_circle_green_24dp);
        } else {
            holder.imageView_dialog_status.setImageResource(R.drawable.ic_check_circle_green_30dp);
            holder.imageView_status.setImageResource(R.drawable.ic_cancel_red_24dp);
        }

        Picasso.get().load(SharedPrefManager.getImageStudentChoosed(context)).into(holder.image_student_dialog);

        holder.cardViewScholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                holder.dialogNamaMahasiswa.setText(SharedPrefManager.getNameStudentChoosed(context));
                holder.dialogNPMMahasiswa.setText(scholarshipRecord.getNpm());
                holder.dialogStatusAjukan.setText(scholarshipRecord.getStatusAjukan());
                holder.dialogNamaBeasiswa.setText(scholarshipRecord.getNamaBeasiswa());
                holder.dialogJenisBeasiswa.setText(scholarshipRecord.getJenisBeasiswa());
                holder.dialogTahunBeasiswa.setText(scholarshipRecord.getTahunBeasiswa());
                holder.dialogSemesterBeasiswa.setText(scholarshipRecord.getSemester());
                holder.dialogKeteranganBeasiswa.setText(scholarshipRecord.getKeterangan());

                holder.scholarshipDetail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.scholarshipDetail.getWindow().setGravity(Gravity.CENTER);
                holder.scholarshipDetail.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //holder.scholarshipDetail.setCanceledOnTouchOutside(false);
                holder.scholarshipDetail.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                holder.scholarshipDetail.show();
            }
        });

        holder.dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.scholarshipDetail.dismiss();
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listSchedule.get(holder.getAdapterPosition()));
//            }
//        });
    }

    public void setListScholarship(List<ScholarshipRecord> scholarship){
        if (scholarship == null)return;
        this.listScholarship.clear();
        this.listScholarship.addAll(scholarship);
    }

    @Override
    public int getItemCount() {
        return listScholarship.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView nameScholarship, status, semester, yearScholarship, dialogClose,
                dialogNamaMahasiswa, dialogNPMMahasiswa, dialogNamaBeasiswa,
                dialogJenisBeasiswa, dialogTahunBeasiswa, dialogSemesterBeasiswa,
                dialogKeteranganBeasiswa, dialogStatusAjukan;
        ImageView imageView_status, imageView_dialog_status, image_student_dialog;
        Dialog scholarshipDetail;
        CardView cardViewScholarship;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            scholarshipDetail = new Dialog(context);
            scholarshipDetail.setContentView(R.layout.dialog_scholarship_detail);

            dialogNamaMahasiswa = scholarshipDetail.findViewById(R.id.tv_nama_mahasiswa);
            dialogNPMMahasiswa = scholarshipDetail.findViewById(R.id.tv_npm_mahasiswa);
            dialogStatusAjukan = scholarshipDetail.findViewById(R.id.tv_status);
            dialogNamaBeasiswa = scholarshipDetail.findViewById(R.id.tv_nama_beasiswa);
            dialogJenisBeasiswa = scholarshipDetail.findViewById(R.id.tv_jenis_beasiswa);
            dialogTahunBeasiswa = scholarshipDetail.findViewById(R.id.tv_tahun_beasiswa);
            dialogSemesterBeasiswa = scholarshipDetail.findViewById(R.id.tv_semester_beasiswa);
            dialogKeteranganBeasiswa = scholarshipDetail.findViewById(R.id.tv_keterangan_beasiswa);
            imageView_dialog_status = scholarshipDetail.findViewById(R.id.iv_status_beasiswa);
            image_student_dialog = scholarshipDetail.findViewById(R.id.image_student);
            dialogClose = scholarshipDetail.findViewById(R.id.txtclose);

            imageView_status = itemView.findViewById(R.id.iv_status);
            status = itemView.findViewById(R.id.tv_status);
            nameScholarship = itemView.findViewById(R.id.name_scholarship);
            semester = itemView.findViewById(R.id.semester_scholarship);
            yearScholarship = itemView.findViewById(R.id.tahun_scholarship);
            cardViewScholarship = itemView.findViewById(R.id.cardview_scholarship);

        }
    }
}
