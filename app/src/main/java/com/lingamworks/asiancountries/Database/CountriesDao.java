package com.lingamworks.asiancountries.Database;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverter;

import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.Models.Language;

import java.util.ArrayList;
import java.util.List;


@Dao
public abstract class CountriesDao {
    @Insert
    @TypeConverter
    abstract void insertcountry(Country country);

    @Insert
    abstract void insertLanguage(Language language);

    public void insertAll(Country country){
        insertcountry(country);
        for (Language value: country.getLanguages()) {
        value.setCountryId(country.getNumericCode());
        insertLanguage(value);
        }
    }

    @Query("select * from Country")
    public abstract List<newCountry> countryList();

    @Query("select * from Country where numericCode=:code")
    public abstract newCountry countrydetail(String code);

    @Query("select name,numericCode from Country where  alpha3Code in(:list)")
    public abstract List<newCountry> getbordercountries(List<String> list);
}
