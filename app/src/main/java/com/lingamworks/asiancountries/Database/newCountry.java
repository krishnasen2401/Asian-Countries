package com.lingamworks.asiancountries.Database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.Models.Language;

import java.util.List;

public class newCountry {
@Embedded
    public Country country;
    @Relation(parentColumn = "numericCode", entityColumn = "countryid")
    public List<Language> languages;

    public List<Language> getLanguages() {
        return languages;
    }

    public Country getCountry() {
        return country;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
