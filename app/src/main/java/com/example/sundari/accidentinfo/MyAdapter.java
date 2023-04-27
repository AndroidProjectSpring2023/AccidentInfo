package com.example.sundari.accidentinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Accident_Info> dataList;

    public MyAdapter(Context context, List<Accident_Info> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //recCard, vImage, vName, vAge, location, incuranceCompany, fileName, policy_No.
        Glide.with(context).load(dataList.get(position).getvImage()).into(holder.vImage);

        TextView vName = holder.itemView.findViewById(R.id.vName);
        vName.setText(dataList.get(position).getvName());
        TextView vAge = holder.itemView.findViewById(R.id.vAge);
        vAge.setText(dataList.get(position).getvAge());
        TextView incuranceCompany = holder.itemView.findViewById(R.id.incuranceCompany);
        String insuranceCompany = dataList.get(position).getIncuranceCompany();
        if(insuranceCompany.equalsIgnoreCase("Not have an Insurance")){
            insuranceCompany = "--Nill--";
        }
        incuranceCompany.setText(insuranceCompany);

//        TextView location = holder.itemView.findViewById(R.id.location);
//        location.setText(dataList.get(position).getLocation());
//        TextView fileName = holder.itemView.findViewById(R.id.fileName);
//        fileName.setText(String.valueOf(dataList.get(position).getFileName()));
//        TextView policy_No = holder.itemView.findViewById(R.id.policy_No);
//        policy_No.setText(dataList.get(position).getPolicy_No());


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("fileName", String.valueOf(dataList.get(holder.getBindingAdapterPosition()).getFileName()));
                intent.putExtra("Image", String.valueOf(dataList.get(holder.getBindingAdapterPosition()).getvImage()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<Accident_Info> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView vImage;
        TextView vName, vAge, location, incuranceCompany, fileName, policy_No;
        CardView recCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//          recCard, vImage, vName, vAge, location, incuranceCompany, fileName, policy_No.

            recCard = itemView.findViewById(R.id.recCard);
            vImage = itemView.findViewById(R.id.vImage);
            vName = itemView.findViewById(R.id.Victim_Name);
            vAge = itemView.findViewById(R.id.vAge);
//            location = itemView.findViewById(R.id.location);
            incuranceCompany = itemView.findViewById(R.id.incuranceCompany);
//            fileName = itemView.findViewById(R.id.fileName);
//            policy_No = itemView.findViewById(R.id.policy_No);

        }
    }
}



