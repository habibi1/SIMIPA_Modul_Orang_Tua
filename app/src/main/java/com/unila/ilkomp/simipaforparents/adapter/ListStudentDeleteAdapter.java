package com.unila.ilkomp.simipaforparents.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentModel;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationModel;
import com.unila.ilkomp.simipaforparents.model.NotificationResponce;
import com.unila.ilkomp.simipaforparents.model.NotificationSender;
import com.unila.ilkomp.simipaforparents.model.StudentRecord;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unila.ilkomp.simipaforparents.util.ControlUtil.elapseClick;

public class ListStudentDeleteAdapter extends RecyclerView.Adapter<ListStudentDeleteAdapter.ListViewHolder>{

    private Boolean reload = false;
    private List<StudentRecord> listStudents = new ArrayList<>();
    Context context;
    private ListStudentDeleteAdapter.OnItemClickCallback onItemClickCallback;
    private long mLastClickTime = 0;

    Fragment fragment;

    public void setOnItemClickCallback(ListStudentDeleteAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(StudentRecord data);
    }

    public ListStudentDeleteAdapter(Context context, ProfileFragment profileFragment) {
        this.context = context;
        this.fragment = profileFragment;
    }

    public ListStudentDeleteAdapter(List<StudentRecord> list){
        this.listStudents = list;
    }

    public void setListStudents(List<StudentRecord> students){
        if (students == null)return;
        this.listStudents.clear();
        this.listStudents.addAll(students);
    }

    @Override
    public ListStudentDeleteAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_student_delete, parent, false);
        return new ListStudentDeleteAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStudentDeleteAdapter.ListViewHolder holder, int position) {
        StudentRecord student = listStudents.get(position);
        holder.nama.setText(student.getNameStudent());
        holder.npm.setText(student.getNPM());

        Glide.with(context)
                .load(student.getFoto())
                .placeholder(R.drawable.ic_person_white)
                .into(holder.foto);

        if (student.getStatus().contains("Belum")) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.confirm.setText("Belum terkonfirmasi");
        } else if (student.getStatus().contains("Ditolak")) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.confirm.setText("Ditolak");
        } else {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.confirm.setText("");
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.viewDialogConfirmDelete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.viewDialogConfirmDelete.getWindow().setGravity(Gravity.BOTTOM);
                holder.viewDialogConfirmDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.viewDialogConfirmDelete.setCanceledOnTouchOutside(false);
                holder.viewDialogConfirmDelete.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                holder.viewDialogConfirmDelete.show();
            }
        });

        holder.dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapseClick();
                holder.viewDialogConfirmDelete.dismiss();

                if (reload)
                    ((ProfileFragment) fragment).getData();
            }
        });

        holder.dialogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData(student.getNPM(), SharedPrefManager.getPhoneNumberLoggedInUser(context), holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Dialog viewDialogConfirmDelete;
        CardView dialogDelete, dialogCancel;
        View dialogViewConfirmDelete, dialogViewLoadingDelete;

        TextView nama, npm, confirm, dialogTextConfirmDelete, dialogTextCancel;
        ImageView foto, delete, dialogIconConfirmDelete, dialogScaleConfirmDelete;

        LinearLayout dialogViewButton, dialogViewButtonDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            viewDialogConfirmDelete = new Dialog(context);
            viewDialogConfirmDelete.setContentView(R.layout.dialog_confirm_delete_student);

            dialogDelete = viewDialogConfirmDelete.findViewById(R.id.cv_delete);
            dialogCancel = viewDialogConfirmDelete.findViewById(R.id.cv_cancel);
            dialogTextConfirmDelete = viewDialogConfirmDelete.findViewById(R.id.text_confirm_delete);
            dialogIconConfirmDelete = viewDialogConfirmDelete.findViewById(R.id.icon_confirm_delete);
            dialogViewConfirmDelete = viewDialogConfirmDelete.findViewById(R.id.view_delete);
            dialogViewLoadingDelete = viewDialogConfirmDelete.findViewById(R.id.view_loading_delete);
            dialogScaleConfirmDelete = viewDialogConfirmDelete.findViewById(R.id.scale_vertical);
            dialogTextCancel = viewDialogConfirmDelete.findViewById(R.id.tv_batal);
            dialogViewButton = viewDialogConfirmDelete.findViewById(R.id.button);
            dialogViewButtonDelete = viewDialogConfirmDelete.findViewById(R.id.view_button_delete);

            nama = itemView.findViewById(R.id.tv_item_name);
            npm = itemView.findViewById(R.id.tv_item_npm);
            confirm = itemView.findViewById(R.id.confirm);
            foto = itemView.findViewById(R.id.img_item_photo);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public void DeleteData (String npm, String no_hp, @NonNull ListStudentDeleteAdapter.ListViewHolder holder) {

        holder.dialogViewConfirmDelete.setVisibility(View.GONE);
        holder.dialogViewLoadingDelete.setVisibility(View.VISIBLE);

        reload = true;

        DeleteStudentModel deleteStudentModel = new DeleteStudentModel(npm, no_hp);

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<DeleteStudentResponce> deleteStudent = apiService.deleteStudent(deleteStudentModel);

        deleteStudent.enqueue(new Callback<DeleteStudentResponce>() {
            @Override
            public void onResponse(Call<DeleteStudentResponce> call, retrofit2.Response<DeleteStudentResponce> response) {

                if (response.code() != 500){

                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200){

                        sendNotifications(response.body().getRecords().get(0).getToken(),
                                context.getResources().getString(R.string.student_notification_title_id_2),
                                SharedPrefManager.getNameLoggedInUser(context)+
                                        " ("+SharedPrefManager.getPhoneNumberLoggedInUser(context)+") "+
                                        context.getResources().getString(R.string.student_notification_message_id_2),
                                context.getResources().getString(R.string.student_notification_channel_name_2),
                                context.getResources().getString(R.string.student_notification_group_name_2),
                                "123",
                                SharedPrefManager.getPhoneNumberLoggedInUser(context),
                                SharedPrefManager.getImageParent(context));

                        if (npm.equals(SharedPrefManager.getNPMChoosed(context))) {
                            holder.viewDialogConfirmDelete.dismiss();
                            ((ProfileFragment) fragment).backToListStudent();
                        } else {
                            holder.viewDialogConfirmDelete.dismiss();
                            ((ProfileFragment)fragment).getData();
                        }

                        Toast.makeText(context, context.getResources().getString(R.string.berhasil_dihapus), Toast.LENGTH_SHORT).show();

                    } else {

                        holder.viewDialogConfirmDelete.dismiss();
                        ((ProfileFragment)fragment).getData();

                        Toast.makeText(context, context.getResources().getString(R.string.gagal_dihapus), Toast.LENGTH_SHORT).show();


                    }
                } else {

                    holder.viewDialogConfirmDelete.dismiss();
                    ((ProfileFragment)fragment).getData();

                    Toast.makeText(context, context.getResources().getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteStudentResponce> call, Throwable t) {

                holder.viewDialogConfirmDelete.dismiss();
                ((ProfileFragment)fragment).getData();

                Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void sendNotifications(String usertoken, String title, String message, String channel_id, String group_id, String id, String user, String photo_path) {
        NotificationModel data = new NotificationModel(title, message, channel_id, group_id, id, user, photo_path);
        NotificationSender sender = new NotificationSender(data, usertoken);

        Log.d("Token", "upload");

        ApiService apiService = Client.getClientNotification().create(ApiService.class);

        Call<NotificationResponce> sendNotifcation = apiService.sendNotifcation(sender);
        sendNotifcation.enqueue(new Callback<NotificationResponce>() {
            @Override
            public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                Log.d("Token", response.code()+"");
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(context, "Failed ", Toast.LENGTH_LONG);
                    } else {
                        Log.d("Token", "Notification Success :)");
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponce> call, Throwable t) {
                Log.d("Token", "failed");
            }
        });
    }
}
