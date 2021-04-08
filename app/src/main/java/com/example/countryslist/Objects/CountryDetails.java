package com.example.countryslist.Objects;

import com.smaspe.iterables.FuncIter;

import java.util.ArrayList;
import java.util.Collection;

public class CountryDetails {

    private String CountryName;
    private String nativeName;
    private ArrayList<String> borders;
    private String flag;
    private String alphaThreeCode;

    public CountryDetails() {
        this.CountryName = "";
        this.nativeName = "";
        this.borders = new ArrayList<>();
        this.alphaThreeCode = "";
        this.flag = "";

    }

    public String getCountryName() {
        return CountryName;
    }

    public CountryDetails setCountryName(String countryName) {
        CountryName = countryName;
        return this;
    }

    public String getNativeName() {
        return nativeName;
    }

    public CountryDetails setNativeName(String nativeName) {
        this.nativeName = nativeName;
        return this;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public CountryDetails setBorders(ArrayList<String> borders) {
        this.borders = borders;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public CountryDetails setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public String getAlphaThreeCode() {
        return alphaThreeCode;
    }

    public CountryDetails setAlphaThreeCode(String alphaThreeCode) {
        this.alphaThreeCode = alphaThreeCode;
        return this;
    }

    /*
    the method gets all the countries and the country we want to find the borders
    and return all the borders according to the country
     */
    public static ArrayList<CountryDetails> findByAlphaThreeCode(Collection<CountryDetails> listCountryDetails, CountryDetails countryDetail) {

        ArrayList<CountryDetails> squaresOfEven;
        ArrayList<CountryDetails> CountriesBorders = new ArrayList<>();
        for (int i = 0; i < countryDetail.getBorders().size(); i++) {
            String searchAlpha = countryDetail.getBorders().get(i);
            squaresOfEven = FuncIter.from(listCountryDetails)
                    .filter((details) -> details.getAlphaThreeCode().equals(searchAlpha))
                    .map((details) -> details)
                    .collect();
            if (!squaresOfEven.isEmpty()) {
                CountriesBorders.add(squaresOfEven.get(0));
            }
        }


        return CountriesBorders;
    }
}