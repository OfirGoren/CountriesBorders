package com.example.countryslist.CallBacks;

import com.example.countryslist.Objects.CountryDetails;

import java.util.ArrayList;

public interface CallBackBordersList {
    void CallBackBorderList(ArrayList<CountryDetails> borderList);

    ArrayList<CountryDetails> callBackGetBorderList();
}
