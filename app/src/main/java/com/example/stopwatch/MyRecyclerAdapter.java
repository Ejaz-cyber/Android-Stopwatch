package com.example.stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.myViewHolder> {

    List<String> lapList;
    private myRecyclerViewInterface recyclerViewInterface;

    public MyRecyclerAdapter(List<String> lapList, myRecyclerViewInterface recyclerViewInterface) {
        this.lapList = lapList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // here we have to inflate layout
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_list,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.lapTime.setText(lapList.get(position));
        holder.serialNo.setText(String.valueOf(position+1));


    }

    @Override
    public int getItemCount() {
        return lapList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, lapTime;
        ImageButton delBtn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            serialNo = itemView.findViewById(R.id.slno);
            lapTime = itemView.findViewById(R.id.lap);
            delBtn = itemView.findViewById(R.id.imgBtn_deletFromList);

            // here we can implement onclicklistener
            // but  its not the best possible way

            // the best way is to implement interface
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewInterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
