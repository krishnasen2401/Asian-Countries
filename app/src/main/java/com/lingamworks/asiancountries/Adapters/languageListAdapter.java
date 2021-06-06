package com.lingamworks.asiancountries.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lingamworks.asiancountries.Activities.CountryDetailsActivity;
import com.lingamworks.asiancountries.Database.newCountry;
import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.Models.Language;
import com.lingamworks.asiancountries.databinding.SingleextviewrowBinding;

import java.util.List;

public class languageListAdapter extends RecyclerView.Adapter<languageListAdapter.ViewHolder> {
    private Context mContext1;
    private List<Language> mlists;
    public class ViewHolder extends RecyclerView.ViewHolder{

        SingleextviewrowBinding binding;//Name of the test_list_item.xml in camel case + "Binding"
        public ViewHolder(SingleextviewrowBinding b){
            super(b.getRoot());
            binding = b;
        }
    }
    public languageListAdapter(Context context,List<Language> mlists) {
        mContext1 = context;
        this.mlists=mlists;
    }
    @NonNull
    @Override
    public languageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
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
       final Language al = mlists.get(position);

        holder.binding.rvtextView.setText(al.getName());
    }

}
