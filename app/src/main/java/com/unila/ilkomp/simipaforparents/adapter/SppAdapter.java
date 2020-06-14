package com.unila.ilkomp.simipaforparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.model.ScholarshipRecord;

import java.util.ArrayList;
import java.util.List;

public class SppAdapter extends RecyclerView.Adapter<SppAdapter.ListViewHolder> {
    Context context;
    private List<ScholarshipRecord> listScholarship = new ArrayList<>();
    private OnItemClickCallback listener;
    private SppAdapter.OnItemClickCallback onItemClickCallback;

    public SppAdapter(ArrayList<ScholarshipRecord> list) {
        this.listScholarship = list;
    }

    public SppAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickCallback(SppAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public SppAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_spp, parent, false);
        return new SppAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SppAdapter.ListViewHolder holder, int position) {

        String semester;
        if ((position + 1) % 2 == 0)
            semester = " Genap";
        else
            semester = " Ganjil";

        ScholarshipRecord scholarshipRecord = listScholarship.get(position);
        holder.tvCountSemester.setText(position + 1 + "");
        holder.tvNameTahunSemester.setText(2016 + position / 2 + semester);
        holder.tvIps.setText("IPS: 4.0");
        holder.tvSks.setText("SKS: 24");

        if (semester.contains("Ganjil"))
            holder.tvStatus.setText("Aktif");
        else
            holder.tvStatus.setText("Tidak Aktif");

        if (holder.tvStatus.getText().toString().contains("Tidak")) {
            holder.ivStatus.setImageResource(R.drawable.ic_cancel_red_24dp);
        } else {
            holder.ivStatus.setImageResource(R.drawable.ic_check_circle_green_24dp);
        }
    }

    public void setListScholarship(List<ScholarshipRecord> scholarship) {
        if (scholarship == null) return;
        this.listScholarship.clear();
        this.listScholarship.addAll(scholarship);
    }

    @Override
    public int getItemCount() {
        return listScholarship.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(ScholarshipRecord data);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountSemester, tvNameTahunSemester, tvSks, tvIps, tvStatus;
        ImageView ivStatus;
        CardView cvSpp;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCountSemester = itemView.findViewById(R.id.tv_count_semester);
            tvNameTahunSemester = itemView.findViewById(R.id.tv_name_tahun_semester);
            tvSks = itemView.findViewById(R.id.tv_sks);
            tvIps = itemView.findViewById(R.id.tv_ips);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivStatus = itemView.findViewById(R.id.iv_status);
            cvSpp = itemView.findViewById(R.id.cardview_spp);

        }
    }
}
