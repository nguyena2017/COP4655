package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>
{

    Context context;
    ArrayList<String> companyList;


    public FavoriteAdapter(Context context, ArrayList<String> companyList)
    {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position)
    {
        holder.rowCompany.setText(companyList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return companyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowCompany;

        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            rowCompany = itemView.findViewById(R.id.company);
        }
    }
}
