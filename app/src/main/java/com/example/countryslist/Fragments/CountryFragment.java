package com.example.countryslist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countryslist.CallBacks.CallBackBordersList;
import com.example.countryslist.CallBacks.CallBackCountryList;
import com.example.countryslist.Objects.CountryDetails;
import com.example.countryslist.Objects.RecyclerViewAdapter;
import com.example.countryslist.Objects.volleyManager;
import com.example.countryslist.R;

import java.util.ArrayList;

public class CountryFragment extends Fragment implements CallBackCountryList, RecyclerViewAdapter.ItemClickListener {

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SearchView searchView;
    private ArrayList<CountryDetails> countryListUpdatedSearch;
    private ArrayList<CountryDetails> allCountry;
    private CallBackBordersList callBackBordersList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.countries_activity, container, false);
        findViewsId(view);
        countryListUpdatedSearch = new ArrayList<>();
        volleyManager volleyManager = new volleyManager(this.context.getApplicationContext());
        volleyManager.initCallBack(this);
        volleyManager.jsonParse();

        searchCountry();

        return view;
    }

    private void findViewsId(View view) {
        recyclerView = view.findViewById(R.id.AllCountryRecyclerView);
        searchView = view.findViewById(R.id.searchView);
    }

    private void filterTheCountry(String value) {
        if (adapter != null) {
            adapter.getFilter().filter(value);
        }
    }

    private void searchCountry() {
        if (countryListUpdatedSearch != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    filterTheCountry(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterTheCountry(newText);
                    return false;
                }
            });
        }
    }

    public void initCallBack(CallBackBordersList callBackBordersList) {
        this.callBackBordersList = callBackBordersList;
    }

    // initialize the recyclerview
    @Override
    public void callBackCountyList(ArrayList<CountryDetails> countryList) {
        // save countries list to new array
        // (when we want to search country in SearchView country the list will change )
        this.countryListUpdatedSearch = countryList;
        // copy countries details to new array (doesn't change after we search country in searchView)
        this.allCountry = new ArrayList<>(countryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        makeLineBetweenRows();
        adapter = new RecyclerViewAdapter(getActivity(), context, this.countryListUpdatedSearch);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    private void makeLineBetweenRows() {
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(View view, int position) {
        // gets array of borders from the country we selected (from the searchView Or Straight from list)
        ArrayList<CountryDetails> CountryBorders = CountryDetails.findByAlphaThreeCode
                (this.allCountry, this.countryListUpdatedSearch.get(position));

        //pass the CountryBorders list to mainActivity
        if (this.callBackBordersList != null) {
            this.callBackBordersList.CallBackBorderList(CountryBorders);
        }
    }


}