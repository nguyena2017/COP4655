package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>
{

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> cityList;
    ArrayList<String> phoneList;
    ArrayList<String> closedList;
    ArrayList<String> distanceList;
    ArrayList<String> ratingList;


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowName;
        TextView rowAddress;
        TextView rowPhone;
        TextView rowClosed;
        TextView rowDistance;
        TextView rowRating;
        TextView rowCity;
        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            rowName = itemView.findViewById(R.id.name);
            rowAddress = itemView.findViewById(R.id.address);
            rowPhone = itemView.findViewById(R.id.phone);
            rowClosed = itemView.findViewById(R.id.open);
            rowDistance = itemView.findViewById(R.id.distance);
            rowRating = itemView.findViewById(R.id.rating);
            rowCity = itemView.findViewById(R.id.city);
        }
    }

    public ProgramAdapter(Context context, ArrayList<String> nameList , ArrayList<String> addressList,
                          ArrayList<String> phoneList, ArrayList<String> closedList, ArrayList<String> distanceList,
                          ArrayList<String> ratingList, ArrayList<String> cityList)
    {
        this.context = context;
        this.nameList = nameList;
        this.addressList = addressList;
        this.phoneList = phoneList;
        this.closedList = closedList;
        this.distanceList = distanceList;
        this.ratingList = ratingList;
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter.ViewHolder holder, int position)
    {
        holder.rowName.setText(nameList.get(position));
        holder.rowAddress.setText(addressList.get(position));
        holder.rowPhone.setText(phoneList.get(position));
        holder.rowClosed.setText(closedList.get(position));
        holder.rowDistance.setText(distanceList.get(position));
        holder.rowRating.setText(ratingList.get(position));
        holder.rowCity.setText(cityList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return nameList.size();
    }
}
