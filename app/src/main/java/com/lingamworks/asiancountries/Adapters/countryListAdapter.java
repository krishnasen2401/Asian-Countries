package com.lingamworks.asiancountries.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lingamworks.asiancountries.Activities.CountryDetailsActivity;
import com.lingamworks.asiancountries.Database.newCountry;
import com.lingamworks.asiancountries.Activities.MainActivity;
import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.databinding.SingleextviewrowBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class countryListAdapter extends RecyclerView.Adapter<countryListAdapter.ViewHolder> {
    private Context mContext1;
    private List<newCountry> mlists;
    public class ViewHolder extends RecyclerView.ViewHolder{

        SingleextviewrowBinding binding;//Name of the test_list_item.xml in camel case + "Binding"
        public ViewHolder(SingleextviewrowBinding b){
            super(b.getRoot());
            binding = b;
        }
    }
    public countryListAdapter(Context context) {
        mContext1 = context;
    }
    @NonNull
    @Override
    public countryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                    int viewType) {
        return new ViewHolder(SingleextviewrowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    @Override
    public int getItemCount() {
        if(mlists==null)
            return 0;
        return mlists.size();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       final newCountry al = mlists.get(position);
       Country finalvalue=al.getCountry();
       finalvalue.setLanguages(al.getLanguages());
        holder.binding.rvtextView.setText(al.getCountry().getName());
        holder.binding.rvtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext1, CountryDetailsActivity.class);
                intent.putExtra("Country Details", finalvalue.getNumericCode());
                mContext1.startActivity(intent);
            }
        });
    }
    public void setTasks(List<newCountry> lists) {
        mlists = lists;
        notifyDataSetChanged();
    }
}
