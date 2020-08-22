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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unila.ilkomp.simipaforparents.HomeActivity;
import com.unila.ilkomp.simipaforparents.R;
import com.unila.ilkomp.simipaforparents.SharedPrefManager;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentModel;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentRecord;
import com.unila.ilkomp.simipaforparents.model.DeleteStudentResponce;
import com.unila.ilkomp.simipaforparents.model.StudentRecord;
import com.unila.ilkomp.simipaforparents.retrofit.ApiService;
import com.unila.ilkomp.simipaforparents.retrofit.Client;
import com.unila.ilkomp.simipaforparents.ui.profile.ProfileFragment;
import com.unila.ilkomp.simipaforparents.util.NotificationControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ListStudentDeleteAdapter extends RecyclerView.Adapter<ListStudentDeleteAdapter.ListViewHolder> implements View.OnClickListener {

    ListStudentDeleteAdapter.ListViewHolder holder;
    StudentRecord student;
    private Boolean reload = false;
    private List<StudentRecord> listStudents = new ArrayList<>();
    Context context;

    Fragment fragment;

    public ListStudentDeleteAdapter(Context context, ProfileFragment profileFragment) {
        this.context = context;
        this.fragment = profileFragment;
    }

    public void setListStudents(List<StudentRecord> students){
        if (students == null)return;
        this.listStudents.clear();
        this.listStudents.addAll(students);
    }

    @NonNull
    @Override
    public ListStudentDeleteAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_student_delete, parent, false);
        return new ListStudentDeleteAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStudentDeleteAdapter.ListViewHolder holder, int position) {
        this.holder = holder;

        student = listStudents.get(position);
        holder.nama.setText(student.getNameStudent());
        holder.npm.setText(student.getNPM());

        Glide.with(context)
                .load(student.getFoto())
                .placeholder(R.drawable.ic_person_white)
                .into(holder.foto);

        if (student.getStatus().contains(context.getString(R.string.belum))) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.cancelRequest.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
            holder.confirm.setText(context.getString(R.string.belum_dikonfirmasi));
        } else if (student.getStatus().contains(context.getString(R.string.ditolak))) {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.cancelRequest.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.confirm.setText(context.getString(R.string.ditolak));
        } else {
            holder.confirm.setVisibility(View.VISIBLE);
            holder.cancelRequest.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.confirm.setText(context.getString(R.string.null_string));
        }

        holder.delete.setOnClickListener(this);
        holder.dialogCancel.setOnClickListener(this);
        holder.dialogDelete.setOnClickListener(this);
        holder.cancelRequest.setOnClickListener(this);
        holder.dialogCancelRequest.setOnClickListener(this);
        holder.dialogCancelCancelRequest.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                Objects.requireNonNull(holder.viewDialogConfirmDelete.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.viewDialogConfirmDelete.getWindow().setGravity(Gravity.BOTTOM);
                holder.viewDialogConfirmDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.viewDialogConfirmDelete.setCanceledOnTouchOutside(false);
                holder.viewDialogConfirmDelete.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                holder.viewDialogConfirmDelete.show();
                break;
            case R.id.cv_cancel:
                holder.viewDialogConfirmDelete.dismiss();

                if (reload)
                    ((ProfileFragment) fragment).getData();
                break;
            case R.id.cv_delete:
                DeleteData(student.getNPM(), SharedPrefManager.getPhoneNumberLoggedInUser(context), holder);
                break;
            case R.id.iv_cancel_request:
                Objects.requireNonNull(holder.viewDialogConfirmCancelRequest.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                holder.viewDialogConfirmCancelRequest.getWindow().setGravity(Gravity.BOTTOM);
                holder.viewDialogConfirmCancelRequest.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.viewDialogConfirmCancelRequest.setCanceledOnTouchOutside(false);
                holder.viewDialogConfirmCancelRequest.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                holder.viewDialogConfirmCancelRequest.show();
                break;
            case R.id.cv_cancel_request:
                CancelRequest(student.getNPM(), SharedPrefManager.getPhoneNumberLoggedInUser(context), holder);
                break;
            case R.id.cv_cancel_cancel_request:
                holder.viewDialogConfirmCancelRequest.dismiss();
                break;
        }
    }

    public void DeleteData (String npm, String no_hp, @NonNull ListStudentDeleteAdapter.ListViewHolder holder) {

        holder.dialogViewConfirmDelete.setVisibility(View.GONE);
        holder.dialogViewLoadingDelete.setVisibility(View.VISIBLE);

        reload = true;

        DeleteStudentModel deleteStudentModel = new DeleteStudentModel(npm, no_hp, SharedPrefManager.getJWT(context));

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<DeleteStudentResponce> deleteStudent = apiService.deleteStudent(deleteStudentModel);

        deleteStudent.enqueue(new Callback<DeleteStudentResponce>() {
            @Override
            public void onResponse(@NonNull Call<DeleteStudentResponce> call, @NonNull retrofit2.Response<DeleteStudentResponce> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {

                        for (DeleteStudentRecord deleteStudentRecord : response.body().getRecords()) {
                            if (!deleteStudentRecord.getToken().isEmpty()) {
                                NotificationControl.sendNotifications(context,
                                        deleteStudentRecord.getToken(),
                                        context.getResources().getString(R.string.student_notification_title_id_2),
                                        SharedPrefManager.getNameLoggedInUser(context) +
                                                " (" + SharedPrefManager.getPhoneNumberLoggedInUser(context) + ") " +
                                                context.getResources().getString(R.string.student_notification_message_id_2),
                                        context.getResources().getString(R.string.student_notification_channel_name_2),
                                        context.getResources().getString(R.string.student_notification_group_name_2),
                                        response.body().getIdRelasi(),
                                        SharedPrefManager.getPhoneNumberLoggedInUser(context),
                                        SharedPrefManager.getImageParent(context));
                            }
                        }

                        if (npm.equals(SharedPrefManager.getNPMChoosed(context))) {
                            holder.viewDialogConfirmDelete.dismiss();
                            ((HomeActivity) context).onBackPressed();
                        } else {
                            holder.viewDialogConfirmDelete.dismiss();
                            ((ProfileFragment) fragment).getData();
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
            public void onFailure(@NonNull Call<DeleteStudentResponce> call, @NonNull Throwable t) {

                holder.viewDialogConfirmDelete.dismiss();
                ((ProfileFragment) fragment).getData();

                Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void CancelRequest(String npm, String no_hp, @NonNull ListStudentDeleteAdapter.ListViewHolder holder) {

        holder.dialogViewConfirmCancelRequest.setVisibility(View.GONE);
        holder.dialogViewLoadingCancelRequest.setVisibility(View.VISIBLE);

        reload = true;

        DeleteStudentModel deleteStudentModel = new DeleteStudentModel(npm, no_hp, SharedPrefManager.getJWT(context));

        ApiService apiService = Client.getClient().create(ApiService.class);

        Call<DeleteStudentResponce> deleteStudent = apiService.deleteStudent(deleteStudentModel);

        deleteStudent.enqueue(new Callback<DeleteStudentResponce>() {
            @Override
            public void onResponse(@NonNull Call<DeleteStudentResponce> call, @NonNull retrofit2.Response<DeleteStudentResponce> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {

                        for (DeleteStudentRecord deleteStudentRecord : response.body().getRecords()) {
                            if (!deleteStudentRecord.getToken().isEmpty()) {
                                NotificationControl.sendNotifications(context,
                                        deleteStudentRecord.getToken(),
                                        context.getResources().getString(R.string.student_notification_title_id_3),
                                        SharedPrefManager.getNameLoggedInUser(context) +
                                                " (" + SharedPrefManager.getPhoneNumberLoggedInUser(context) + ") " +
                                                context.getResources().getString(R.string.student_notification_message_id_3),
                                        context.getResources().getString(R.string.student_notification_channel_name_3),
                                        context.getResources().getString(R.string.student_notification_group_name_3),
                                        response.body().getIdRelasi(),
                                        SharedPrefManager.getPhoneNumberLoggedInUser(context),
                                        SharedPrefManager.getImageParent(context));
                            }
                        }

                        holder.viewDialogConfirmCancelRequest.dismiss();
                        ((ProfileFragment) fragment).getData();

                        Toast.makeText(context, context.getResources().getString(R.string.berhasil_dihapus), Toast.LENGTH_SHORT).show();

                    } else {

                        holder.viewDialogConfirmCancelRequest.dismiss();
                        ((ProfileFragment) fragment).getData();

                        Toast.makeText(context, context.getResources().getString(R.string.gagal_dihapus), Toast.LENGTH_SHORT).show();


                    }
                } else {

                    holder.viewDialogConfirmCancelRequest.dismiss();
                    ((ProfileFragment) fragment).getData();

                    Toast.makeText(context, context.getResources().getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteStudentResponce> call, @NonNull Throwable t) {

                holder.viewDialogConfirmCancelRequest.dismiss();
                ((ProfileFragment) fragment).getData();

                Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Dialog viewDialogConfirmDelete;
        CardView dialogDelete, dialogCancel;
        View dialogViewConfirmDelete, dialogViewLoadingDelete;

        TextView nama, npm, confirm, dialogTextConfirmDelete, dialogTextCancel;
        ImageView foto, delete, cancelRequest, dialogIconConfirmDelete, dialogScaleConfirmDelete;

        LinearLayout dialogViewButton, dialogViewButtonDelete;

        Dialog viewDialogConfirmCancelRequest;
        CardView dialogCancelRequest, dialogCancelCancelRequest;
        View dialogViewConfirmCancelRequest, dialogViewLoadingCancelRequest;

        TextView dialogTextConfirmCancelRequest, dialogTextCancelRequest;
        ImageView dialogIconConfirmCancelRequest, dialogScaleConfirmCancelRequest;

        LinearLayout dialogViewButtonCancelRequest, dialogViewButtonCancelCancelRequest;

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

            viewDialogConfirmCancelRequest = new Dialog(context);
            viewDialogConfirmCancelRequest.setContentView(R.layout.dialog_cancel_request);

            dialogCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.cv_cancel_request);
            dialogCancelCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.cv_cancel_cancel_request);
            dialogTextConfirmCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.text_confirm_cancel_request);
            dialogIconConfirmCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.icon_confirm_cancel_request);
            dialogViewConfirmCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.view_cancel_request);
            dialogViewLoadingCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.view_loading_cancel_request);
            dialogScaleConfirmCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.scale_vertical);
            dialogTextCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.tv_batal_cancel_request);
            dialogViewButtonCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.button_cancel_request);
            dialogViewButtonCancelCancelRequest = viewDialogConfirmCancelRequest.findViewById(R.id.view_button_cancel_request);

            nama = itemView.findViewById(R.id.tv_item_name);
            npm = itemView.findViewById(R.id.tv_item_npm);
            confirm = itemView.findViewById(R.id.confirm);
            foto = itemView.findViewById(R.id.img_item_photo);
            delete = itemView.findViewById(R.id.delete);
            cancelRequest = itemView.findViewById(R.id.iv_cancel_request);
        }
    }

/*    public void sendNotifications(String usertoken, String title, String message, String channel_id, String group_id, String id, String user, String photo_path) {
        NotificationModel data = new NotificationModel(title, message, channel_id, group_id, id, user, photo_path);
        NotificationSender sender = new NotificationSender(data, usertoken);

        Log.d("Token", usertoken);

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
    }*/
}
