package com.unila.ilkomp.simipaforparents.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademik;
import com.unila.ilkomp.simipaforparents.model.StudyProgress;

import java.util.ArrayList;

public class StudyProgressAdapter extends RecyclerView.Adapter<StudyProgressAdapter.ListViewHolder>{

    private ArrayList<StudyProgress> listStudyProgress;
    public StudyProgressAdapter(ArrayList<StudyProgress> list) {
        this.listStudyProgress = list;
    }

    private StudyProgressAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(StudyProgressAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public StudyProgressAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_study_progress, parent, false);
        return new StudyProgressAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudyProgressAdapter.ListViewHolder holder, int position) {
        StudyProgress studyProgress = listStudyProgress.get(position);
        holder.semester.setText(studyProgress.getSemester());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listSchedule.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listStudyProgress.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView semester;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            semester = itemView.findViewById(R.id.tv_semester);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(KalenderAkademik data);
    }
}
