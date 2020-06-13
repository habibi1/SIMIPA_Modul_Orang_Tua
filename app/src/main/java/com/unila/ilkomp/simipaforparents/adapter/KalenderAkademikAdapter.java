package com.unila.ilkomp.simipaforparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademik;
import com.unila.ilkomp.simipaforparents.model.KalenderAkademikRecord;

import java.util.ArrayList;
import java.util.List;

public class KalenderAkademikAdapter extends RecyclerView.Adapter<KalenderAkademikAdapter.ListViewHolder>{

    Context context;

    private List<KalenderAkademikRecord> listKalenderAkademik = new ArrayList<>();

    public KalenderAkademikAdapter(Context context) {
        this.context = context;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListKalenderAkademik(List<KalenderAkademikRecord> kalenderAkademik) {
        if (kalenderAkademik == null) return;
        this.listKalenderAkademik.clear();
        this.listKalenderAkademik.addAll(kalenderAkademik);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_kalender_akademik, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        KalenderAkademikRecord kalenderAkademik = listKalenderAkademik.get(position);
        holder.nama_kegiatan.setText(kalenderAkademik.getKegiatan());
        holder.tanggal_kegiatan.setText(kalenderAkademik.getDate() + " s.d. " + kalenderAkademik.getDueDate());
        holder.date.setText(kalenderAkademik.getDateTanggal());
    }

    @Override
    public int getItemCount() {
        return listKalenderAkademik.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView nama_kegiatan, tanggal_kegiatan, date;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kegiatan = itemView.findViewById(R.id.nama_kegiatan);
            tanggal_kegiatan = itemView.findViewById(R.id.tanggal_kegiatan);
            date = itemView.findViewById(R.id.tv_date);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(KalenderAkademik data);
    }

}
