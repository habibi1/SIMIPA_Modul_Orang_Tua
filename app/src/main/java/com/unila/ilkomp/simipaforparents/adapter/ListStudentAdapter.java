package com.unila.ilkomp.simipaforparents.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.HomeActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.StudentRecord;

import java.util.ArrayList;
import java.util.List;

public class ListStudentAdapter extends RecyclerView.Adapter<ListStudentAdapter.ListViewHolder>{

    private List<StudentRecord> listStudents = new ArrayList<>();
    private Context context;
    private OnItemClickCallback onItemClickCallback;
    private long mLastClickTime = 0;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(StudentRecord data);
    }

    public ListStudentAdapter(Context context) {
        this.context = context;
    }

    public ListStudentAdapter(List<StudentRecord> list){
        this.listStudents = list;
    }

    public void setListStudents(List<StudentRecord> students){
        if (students == null)return;
        this.listStudents.clear();
        this.listStudents.addAll(students);
    }

    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_student, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        StudentRecord student = listStudents.get(position);
        holder.nama.setText(student.getNameStudent());
        holder.npm.setText(student.getNPM());

        Glide.with(context)
                .load(student.getFoto())
                .placeholder(R.drawable.ic_person_white)
                .into(holder.foto);

        if (student.getStatus().contains("Belum")) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.confirm.setText("Belum dikonfirmasi");
        } else if (student.getStatus().contains("Ditolak")) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.confirm.setText("Ditolak");
        } else {
            holder.confirm.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!HomeActivity.active){
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                        onItemClickCallback.onItemClicked(listStudents.get(holder.getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView nama, npm, confirm;
        ImageView foto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_item_name);
            npm = itemView.findViewById(R.id.tv_item_npm);
            confirm = itemView.findViewById(R.id.confirm);
            foto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
