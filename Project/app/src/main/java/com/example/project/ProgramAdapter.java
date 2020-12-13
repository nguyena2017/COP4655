package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>
{

    Context context;
    String[] nameList;
    String[] descriptionList;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowName;
        TextView rowDescription;
        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowDescription = itemView.findViewById(R.id.description);
        }
    }

    public ProgramAdapter(Context context, String[] programNameList, String[] programDescriptionLIst)
    {
        this.context = context;
        nameList = programNameList;
        descriptionList = programDescriptionLIst;

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
        holder.rowName.setText(nameList[position]);
        holder.rowDescription.setText(descriptionList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }
}
