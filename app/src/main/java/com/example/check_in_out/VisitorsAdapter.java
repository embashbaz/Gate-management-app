package com.example.check_in_out;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.VisitorHolder> {
    private List<Visitors> visitors = new ArrayList<>();
    OnItemCLickListerner listener;

    @NonNull
    @Override
    public VisitorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitors_item, parent, false);

        return new VisitorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorHolder holder, int position) {
        Visitors currentVisitor = visitors.get(position);

        String names = ""+currentVisitor.getName()+" "+currentVisitor.getSecondName();
        holder.nameRecycler.setText(names);
        holder.idRecycler.setText(currentVisitor.getIdNumber());

        String timeData = ""+currentVisitor.getTimeIn()+"--"+currentVisitor.getDateIn();
        holder.timeRecycler.setText(timeData);

        if(currentVisitor.isFlagIn() && currentVisitor.isStatus())
            holder.statusImage.setImageResource(R.drawable.in_red);
        else if(!currentVisitor.isFlagIn() && currentVisitor.isStatus())
            holder.statusImage.setImageResource(R.drawable.in_green);
        else if((currentVisitor.isFlagOut() || currentVisitor.isFlagIn()) && !currentVisitor.isStatus())
            holder.statusImage.setImageResource(R.drawable.out_red);
        else
            holder.statusImage.setImageResource(R.drawable.out_green);
    }

    public void setVisitors(List<Visitors> visitors){
        this.visitors = visitors;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return visitors.size();
    }

    class VisitorHolder extends RecyclerView.ViewHolder{
        TextView nameRecycler;
        TextView idRecycler;
        TextView timeRecycler;
        ImageView statusImage;

        public VisitorHolder(@NonNull View itemView) {
            super(itemView);

            nameRecycler = itemView.findViewById(R.id.name_recy);
            timeRecycler = itemView.findViewById(R.id.time_recy);
            idRecycler = itemView.findViewById(R.id.id_recy);
            statusImage = itemView.findViewById(R.id.image_recy);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if(listener!=null && position != RecyclerView.NO_POSITION)
                    listener.onItemCLick(visitors.get(position));
                }
            });
        }
    }

    public interface OnItemCLickListerner{
        void onItemCLick(Visitors visitor);

    }
    public void setOnItemCLickListener(OnItemCLickListerner listener){
            this.listener=listener;
    }
}
