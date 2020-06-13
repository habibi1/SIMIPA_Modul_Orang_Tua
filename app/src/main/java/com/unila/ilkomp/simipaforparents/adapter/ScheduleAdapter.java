package com.unila.ilkomp.simipaforparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.ScheduleRecord;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ListViewHolder>{

    Context context;
    private List<ScheduleRecord> listSchedule = new ArrayList<>();

    public ScheduleAdapter(ArrayList<ScheduleRecord> list) {
        this.listSchedule = list;
    }

    private ScheduleAdapter.OnItemClickCallback onItemClickCallback;

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickCallback(ScheduleAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_schedule, parent, false);
        return new ScheduleAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduleAdapter.ListViewHolder holder, int position) {
        ScheduleRecord schedule = listSchedule.get(position);
        holder.jam.setText(schedule.getMulai().substring(0,5) + "-" + schedule.getSelesai().substring(0,5));
        holder.nama_schedule.setText(schedule.getMataKuliah());
        holder.tempat_schedule.setText(schedule.getRuang());
        holder.dosen_schedule.setText(schedule.getDosenPJ());

        if (schedule.getJenis().equals("Teori"))
            holder.line_schedule.setImageResource(R.drawable.line_schedule_green);
        else if (schedule.getJenis().equals("Praktikum"))
            holder.line_schedule.setImageResource(R.drawable.line_schedule_purle);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listSchedule.get(holder.getAdapterPosition()));
//            }
//        });
    }

    public void setSchedule(List<ScheduleRecord> scheduleRecords){
        if (scheduleRecords == null)return;
        this.listSchedule.clear();
        this.listSchedule.addAll(scheduleRecords);
    }

    @Override
    public int getItemCount() {
        return listSchedule.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView jam, nama_schedule, tempat_schedule, dosen_schedule;
        ImageView line_schedule;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.tv_jam);
            nama_schedule = itemView.findViewById(R.id.tv_schedule);
            tempat_schedule = itemView.findViewById(R.id.tv_place);
            dosen_schedule = itemView.findViewById(R.id.tv_lecture);
            line_schedule = itemView.findViewById(R.id.line_schedule);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ScheduleRecord data);
    }
    
}
