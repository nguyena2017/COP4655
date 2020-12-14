package com.example.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>
{

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> cityList;
    ArrayList<String> phoneList;
    ArrayList<String> closedList;
    ArrayList<String> distanceList;
    ArrayList<String> ratingList;


    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, final int position)
    {
        holder.rowName.setText(nameList.get(position));
        holder.rowAddress.setText(addressList.get(position));
        holder.rowPhone.setText(phoneList.get(position));
        holder.rowClosed.setText(closedList.get(position));
        holder.rowDistance.setText(distanceList.get(position));
        holder.rowRating.setText(ratingList.get(position));
        holder.rowCity.setText(cityList.get(position));
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Favorite favorite = Favorite.getInstance();
                String theRemovedItem = nameList.get(position);
                favorite.add(theRemovedItem);
            }
        });
    }

    public CompanyAdapter(Context context, ArrayList<String> nameList , ArrayList<String> addressList,
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
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.company, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowName;
        TextView rowAddress;
        TextView rowPhone;
        TextView rowClosed;
        TextView rowDistance;
        TextView rowRating;
        TextView rowCity;
        ImageButton favorite;
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
            favorite = itemView.findViewById(R.id.favorite);
        }
    }

    @Override
    public int getItemCount()
    {
        return nameList.size();
    }
}
