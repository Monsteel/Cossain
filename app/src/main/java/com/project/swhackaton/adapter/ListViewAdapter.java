package com.project.swhackaton.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.swhackaton.R;
import com.project.swhackaton.model.ListModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{

    private final List<ListModel> mDataList;

    public ListViewAdapter(List<ListModel> dataList){
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListModel listModel = mDataList.get(position);

        holder.title.setText(listModel.getTitle());
        holder.content.setText("계약자 : "+listModel.getA_id()+"\n피 계약자"+listModel.getB_id());

        if(listModel.isResolve()) {
            holder.status.setTextColor(Color.parseColor("#655EEB"));
            holder.status.setText("완료");
        }else{
            holder.status.setTextColor(Color.parseColor("#D81B60"));
            holder.status.setText("진행중");
        }

        holder.cardView.setOnClickListener(v -> {
            Activity activity = (Activity) holder.content.getContext();
            Intent intent = new Intent(holder.title.getContext(), ListModel.class);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView status;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            status = itemView.findViewById(R.id.status);
        }
    }

}