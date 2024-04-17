package com.example.doan.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.doan.R;
import java.util.List;

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ViewHolder> {

    private List<QueryDocumentSnapshot> chartsList; // Thay đổi kiểu dữ liệu của danh sách

    // Thêm constructor mới để nhận danh sách của QueryDocumentSnapshot
    public ChartsAdapter(Context context, List<QueryDocumentSnapshot> chartsList) {
        this.chartsList = chartsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueryDocumentSnapshot document = chartsList.get(position);

        // Lấy dữ liệu từ QueryDocumentSnapshot
        String userId = document.getString("currentUserId");
        Long correct = document.getLong("correct");
        Long time = document.getLong("time");

        // Đặt dữ liệu vào TextViews
        holder.Nubertxt.setText(String.valueOf(position + 1));


        holder.txtUserId.setText(userId);
        holder.txtCorrect.setText(String.valueOf(correct));
        holder.txtTime.setText(String.valueOf(time));
        if (position ==0){
            holder.Nubertxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
            holder.txtUserId.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
            holder.txtCorrect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
            holder.txtTime.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
            holder.txt3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
            holder.txt4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
        }
        if (position ==1){
            holder.Nubertxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
            holder.txtUserId.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
            holder.txtCorrect.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
            holder.txtTime.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
            holder.txt3.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
            holder.txt4.setTextColor(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
        }
        if (position ==2){
            holder.Nubertxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
            holder.txtUserId.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
            holder.txtCorrect.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
            holder.txtTime.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
            holder.txt3.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
            holder.txt4.setTextColor(ColorStateList.valueOf(Color.parseColor("#7C5301")));
        }
    }

    @Override
    public int getItemCount() {
        return chartsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserId, txtCorrect, txtTime,Nubertxt, txt3, txt4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.Usernametxt);
            txtCorrect = itemView.findViewById(R.id.Scoretxt);
            txt3 = itemView.findViewById(R.id.txtdiem);
            txt4 = itemView.findViewById(R.id.txttime);
            txtTime = itemView.findViewById(R.id.Timetxt);
            Nubertxt = itemView.findViewById(R.id.Nubertxt);
        }
    }
}

