

package com.lingamworks.asiancountries.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.net.Uri;
import android.os.Bundle;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.lingamworks.asiancountries.Adapters.boundaryListAdapter;
import com.lingamworks.asiancountries.Adapters.languageListAdapter;
import com.lingamworks.asiancountries.Database.database;
import com.lingamworks.asiancountries.Database.newCountry;
import com.lingamworks.asiancountries.Models.Language;
import com.lingamworks.asiancountries.databinding.ActivityCountryDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailsActivity extends AppCompatActivity {
    ActivityCountryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCountryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String countrycode=getIntent().getStringExtra("Country Details");
        database db=database.getInstance(this);
        Thread thread=new Thread(){
            @Override
            public void run(){
                newCountry country=db.CountriesDato().countrydetail(countrycode);
                List<newCountry> boundary=db.CountriesDato().getbordercountries(country.getCountry().getBorders());
                runOnUiThread(new Runnable() {
                    public void run() {
                    setdata(country,boundary);
                    }
                });

            }
        };
        thread.start();

    }
    public void setdata(newCountry country, List<newCountry> boundary){
        binding.tvCountryName.setText("Name: "+country.getCountry().getName());
        binding.tvCountryCapital.setText("Capital: "+country.getCountry().getCapital());
        binding.tvPopulation.setText("Population: "+country.getCountry().getPopulation().toString());
        binding.tvRegion.setText("Region: "+country.getCountry().getRegion());
        binding.tvSubRegion.setText("Sub Region:"+country.getCountry().getSubregion());
        List<String> mlistlangauages=new ArrayList<>();
        for(Language value:country.getLanguages()) {
            mlistlangauages.add(value.getName());
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ((SimpleItemAnimator) binding.lvBoundarycList.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) binding.lvLanguageList.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.lvLanguageList.setLayoutManager(mLayoutManager);
        binding.lvBoundarycList.setLayoutManager(mLayoutManager2);
        boundaryListAdapter adapter1=new boundaryListAdapter(CountryDetailsActivity.this,boundary);
        languageListAdapter adapter2=new languageListAdapter(CountryDetailsActivity.this,country.getLanguages());
        binding.lvLanguageList.setAdapter(adapter2);
        binding.lvBoundarycList.setAdapter(adapter1);
        GlideToVectorYou
                .init()
                .with(this)
                .load(Uri.parse(country.getCountry().getFlag()), binding.imgVCountry);


    }
}