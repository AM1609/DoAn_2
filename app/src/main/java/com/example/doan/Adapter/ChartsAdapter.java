package com.example.doan.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.Model.ChartsModel;
import com.example.doan.Model.QuizListModel;
import com.example.doan.R;

import java.util.List;

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ViewHolder> {

    private List<ChartsModel> chartsList;
    private Context context;

    public void setChartListModels(List<ChartsModel> chartListModels) {
        this.chartsList = chartListModels;
    }
    public ChartsAdapter(Context context, List<ChartsModel> chartsList) {
        this.context = context;
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
        ChartsModel chartsModel = chartsList.get(position);

        holder.txtUserId.setText(chartsModel.getCurrentUserId());
        holder.txtCorrect.setText(String.valueOf(chartsModel.getCorrect()));
        holder.txtNotAnswered.setText(String.valueOf(chartsModel.getNotAnswered()));
        holder.txtWrong.setText(String.valueOf(chartsModel.getWrong()));
        holder.txtTime.setText(String.valueOf(chartsModel.getTime()));
    }

    @Override
    public int getItemCount() {
        return chartsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserId, txtCorrect, txtNotAnswered, txtWrong, txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserId = itemView.findViewById(R.id.Usernametxt);
            txtCorrect = itemView.findViewById(R.id.Scoretxt);
            txtTime = itemView.findViewById(R.id.Timetxt);
        }
    }
}
