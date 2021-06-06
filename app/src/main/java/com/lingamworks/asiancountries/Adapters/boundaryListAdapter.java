package com.lingamworks.asiancountries.Adapters;

import android.app.Activity;
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
import com.lingamworks.asiancountries.databinding.SingleextviewrowBinding;

import java.util.List;

public class boundaryListAdapter extends RecyclerView.Adapter<boundaryListAdapter.ViewHolder> {
    private Context mContext1;
    private List<newCountry> mlists;
    public class ViewHolder extends RecyclerView.ViewHolder{

        SingleextviewrowBinding binding;//Name of the test_list_item.xml in camel case + "Binding"
        public ViewHolder(SingleextviewrowBinding b){
            super(b.getRoot());
            binding = b;
        }
    }
    public boundaryListAdapter(Context context,List<newCountry> mlists) {
        mContext1 = context;this.mlists=mlists;
    }
    @NonNull
    @Override
    public boundaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
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
        newCountry finalvalue=mlists.get(position);
        holder.binding.rvtextView.setText(finalvalue.getCountry().getName());
        holder.binding.rvtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext1, CountryDetailsActivity.class);
                intent.putExtra("Country Details", finalvalue.getCountry().getNumericCode());
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext1.startActivity(intent);
                ((Activity)mContext1).finish();
            }
        });
    }
}
