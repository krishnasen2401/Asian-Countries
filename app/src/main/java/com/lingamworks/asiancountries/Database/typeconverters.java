package com.lingamworks.asiancountries.Database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class typeconverters {
    @TypeConverter
    public String listToString(List<String> value){
        if(value==null)
            return null;
        Gson gson=new Gson();
        String finallist=gson.toJson(value);
        return finallist;
    }
    @TypeConverter
    public List<String> stringToList(String data){
        if(data==null)
            return null;
        Gson gson=new Gson();
        Type listType=new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(data,listType);

    }

}
