package com.example.fbuapp.business;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbuapp.R;
import com.example.fbuapp.activities.BusinessDetailsActivity;

import org.parceler.Parcels;

import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<com.example.fbuapp.business.BusinessAdapter.ViewHolder> {
    private Context context;
    private List<Business> allBusinesses;
    private final String TAG = "BusinessAdapter";

    public BusinessAdapter(Context context, List<Business> businesses) {
        this.context = context;
        this.allBusinesses = businesses;
    }

    @NonNull
    @Override
    public com.example.fbuapp.business.BusinessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_business, parent, false);
        return new com.example.fbuapp.business.BusinessAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull com.example.fbuapp.business.BusinessAdapter.ViewHolder holder, int position) {
        Business business = allBusinesses.get(position);
        holder.bind(business);
    }

    @Override
    public int getItemCount() {
        return allBusinesses.size();
    }

    public void clear() {
        allBusinesses.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Business> list) {
        allBusinesses.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView businessName;
        TextView businessAddressLineOne;
        TextView businessAddressLineTwo;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessName = itemView.findViewById(R.id.businessName);
            businessAddressLineOne = itemView.findViewById(R.id.businessAddressLineOne);
            businessAddressLineTwo = itemView.findViewById(R.id.businessAddressLineTwo);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind(Business business) {
            businessName.setText(business.getName());
            businessAddressLineOne.setText(business.getAddressLineOne());
            businessAddressLineTwo.setText(business.getAddressLineTwo());
            ratingBar.setRating(business.getScore());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BusinessDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("business", Parcels.wrap(business));
                    context.startActivity(intent);
                }
            });
        }
    }
}