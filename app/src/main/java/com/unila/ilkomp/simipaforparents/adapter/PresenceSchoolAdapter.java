package com.unila.ilkomp.simipaforparents.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.PresenceSchool;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademik;

import java.util.ArrayList;

public class PresenceSchoolAdapter extends RecyclerView.Adapter<PresenceSchoolAdapter.ListViewHolder>{

    private ArrayList<PresenceSchool> listPresenceSchool;
    public PresenceSchoolAdapter(ArrayList<PresenceSchool> list) {
        this.listPresenceSchool = list;
    }

    private PresenceSchoolAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(PresenceSchoolAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public PresenceSchoolAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_presence_school, parent, false);
        return new PresenceSchoolAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PresenceSchoolAdapter.ListViewHolder holder, int position) {
        PresenceSchool presenceSchool = listPresenceSchool.get(position);
        holder.namaMataKuliah.setText(presenceSchool.getNamaMataKuliah());
        holder.hadir.setText(presenceSchool.getHadir());
        holder.tidakHadir.setText(presenceSchool.getTidakHadir());
        holder.perbandingan.setText(presenceSchool.getPerbandingan());
        holder.persentase.setText(presenceSchool.getPersentase());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listSchedule.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listPresenceSchool.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView namaMataKuliah, hadir, tidakHadir, perbandingan, persentase;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            namaMataKuliah = itemView.findViewById(R.id.tv_mata_kuliah);
            hadir = itemView.findViewById(R.id.tv_hadir);
            tidakHadir = itemView.findViewById(R.id.tv_tidak_hadir);
            perbandingan = itemView.findViewById(R.id.tv_perbandingan);
            persentase = itemView.findViewById(R.id.tv_persentase);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(KalenderAkademik data);
    }
    
}
