package com.example.doan.Adapter;

import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return chartsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserId, txtCorrect, txtTime,Nubertxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.Usernametxt);
            txtCorrect = itemView.findViewById(R.id.Scoretxt);

            txtTime = itemView.findViewById(R.id.Timetxt);
            Nubertxt = itemView.findViewById(R.id.Nubertxt);
        }
    }
}
.
