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
import com.unila.ilkomp.simipaforparents.model.StatusSemesterRecord;

import java.util.ArrayList;
import java.util.List;

public class StatusSemesterAdapter extends RecyclerView.Adapter<StatusSemesterAdapter.ListViewHolder> {
    Context context;
    private List<StatusSemesterRecord> statusSemesterRecords = new ArrayList<>();
    private OnItemClickCallback listener;
    private StatusSemesterAdapter.OnItemClickCallback onItemClickCallback;

    public StatusSemesterAdapter(ArrayList<StatusSemesterRecord> list) {
        this.statusSemesterRecords = list;
    }

    public StatusSemesterAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickCallback(StatusSemesterAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public StatusSemesterAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_spp, parent, false);
        return new StatusSemesterAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusSemesterAdapter.ListViewHolder holder, int position) {

        String semester;
        if ((position + 1) % 2 == 0)
            semester = " Genap";
        else
            semester = " Ganjil";

        StatusSemesterRecord statusSemesterRecord = statusSemesterRecords.get(position);
        holder.tvCountSemester.setText(statusSemesterRecord.getSemester());
        holder.tvNameTahunSemester.setText(statusSemesterRecord.getPeriode());
        holder.tvIps.setText("IPS: " + statusSemesterRecord.getIps());
        holder.tvSks.setText("SKS: " + statusSemesterRecord.getSks());

        holder.tvStatus.setText(statusSemesterRecord.getStatus());

        if (holder.tvStatus.getText().toString().contains("Tidak")) {
            holder.ivStatus.setImageResource(R.drawable.ic_cancel_red_24dp);
        } else {
            holder.ivStatus.setImageResource(R.drawable.ic_check_circle_green_24dp);
        }
    }

    public void setListStatusSemester(List<StatusSemesterRecord> statusSemesterRecords) {
        if (statusSemesterRecords == null) return;
        this.statusSemesterRecords.clear();
        this.statusSemesterRecords.addAll(statusSemesterRecords);
    }

    @Override
    public int getItemCount() {
        return statusSemesterRecords.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(StatusSemesterRecord data);
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
