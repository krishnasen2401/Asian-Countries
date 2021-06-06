package com.lingamworks.asiancountries.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lingamworks.asiancountries.Adapters.countryListAdapter;
import com.lingamworks.asiancountries.Database.database;
import com.lingamworks.asiancountries.Database.newCountry;
import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.R;
import com.lingamworks.asiancountries.Utils;
import com.lingamworks.asiancountries.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
Retrofit retrofit;
ActivityMainBinding binding;
database db;
countryListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=database.getInstance(binding.getRoot().getContext());
        syncdata();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ((SimpleItemAnimator) binding.rvCountryList.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.rvCountryList.setLayoutManager(mLayoutManager);
        adapter=new countryListAdapter(MainActivity.this);
        binding.rvCountryList.setAdapter(adapter);
    }
    void syncdata(){
        retrofit=new Retrofit.Builder()
                .baseUrl(new Utils().Baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getCountries service=retrofit.create(getCountries.class);
        Call<List<Country>> call= service.getallData();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> mlist=new ArrayList<>(response.body());
                Thread thread=new Thread(){
                    @Override
                    public void run(){
                        db.clearAllTables();
                        for(Country value:mlist) {
                            db.CountriesDato().insertAll(value);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this,"Sync done",Toast.LENGTH_LONG).show();
                                getdata();
                            }
                        });

                    }
                };
                thread.start();
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });

    }
    void getdata(){
        Thread thread=new Thread(){
            @Override
            public void run(){
                List<newCountry> mlist=db.CountriesDato().countryList();
                Log.d("test size", String.valueOf(mlist.size()));
                runOnUiThread(new Runnable() {
                    public void run() {
                        //adapter=new countryListAdapter(mlist,MainActivity.this,binding.rvCountryList);
                        Toast.makeText(MainActivity.this,"Fetching from Room Database",Toast.LENGTH_LONG).show();
                        adapter.setTasks(mlist);
                    }
                });

            }
        };
        thread.start();

    }
    void deletedata(){
        Thread thread=new Thread(){
            @Override
            public void run(){
                db.clearAllTables();
                List<newCountry> mlist=db.CountriesDato().countryList();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Deleted Data",Toast.LENGTH_LONG).show();
                        adapter.setTasks(mlist);
                    }
                });

            }
        };
        thread.start();
    }
    public interface getCountries{
        @GET("region/asia")
        Call<List<Country>> getallData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                syncdata();
                return true;
            case R.id.delete:
                deletedata();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}