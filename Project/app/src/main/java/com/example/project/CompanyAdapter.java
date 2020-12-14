package com.example.project;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>
{

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> addressList;
    ArrayList<String> cityList;
    ArrayList<String> phoneList;
    ArrayList<String> closedList;
    ArrayList<String> ratingList;
    ArrayList<String> imageList;
    MainActivity main = new MainActivity();

    public CompanyAdapter(Context context, ArrayList<String> nameList , ArrayList<String> addressList,
                          ArrayList<String> phoneList, ArrayList<String> closedList,
                          ArrayList<String> ratingList, ArrayList<String> cityList, ArrayList<String> imageList)
    {
        this.context = context;
        this.nameList = nameList;
        this.addressList = addressList;
        this.phoneList = phoneList;
        this.closedList = closedList;
        this.ratingList = ratingList;
        this.cityList = cityList;
        this.imageList = imageList;
    }

    @Override
    public void onBindViewHolder(@NonNull final CompanyAdapter.ViewHolder holder, final int position)
    {
        holder.rowName.setText(nameList.get(position));
        holder.rowAddress.setText(addressList.get(position));
        holder.rowPhone.setText(phoneList.get(position));
        holder.rowClosed.setText(closedList.get(position));
        holder.rowRating.setText(ratingList.get(position));
        holder.rowCity.setText(cityList.get(position));
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                holder.favorite.setBackgroundColor(Color.RED);
                String company = nameList.get(position);
                Favorite favorite = Favorite.getInstance();
                favorite.add(company);
            }
        });
        Glide.with(context)
                .load(imageList.get(position))
                .override(300, 450)
                .into(holder.image);

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
        TextView rowRating;
        TextView rowCity;
        ImageButton favorite;
        ImageView image;
        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            rowName = itemView.findViewById(R.id.name);
            rowAddress = itemView.findViewById(R.id.address);
            rowPhone = itemView.findViewById(R.id.phone);
            rowClosed = itemView.findViewById(R.id.open);
            rowRating = itemView.findViewById(R.id.rating);
            rowCity = itemView.findViewById(R.id.city);
            favorite = itemView.findViewById(R.id.favorite);
            image = itemView.findViewById(R.id.picture);
        }
    }

    @Override
    public int getItemCount()
    {
        return nameList.size();
    }
}
