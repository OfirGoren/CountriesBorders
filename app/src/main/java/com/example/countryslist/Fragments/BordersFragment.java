package com.example.countryslist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countryslist.CallBacks.CallBackBordersList;
import com.example.countryslist.Objects.CountryDetails;
import com.example.countryslist.Objects.RecyclerViewAdapter;
import com.example.countryslist.R;

import java.util.ArrayList;

public class BordersFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<CountryDetails> borderList;
    private CallBackBordersList callBackBordersList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.border_fragment, container, false);

        findViewsId(view);
        //get the borderList from MainActivity
        this.borderList = this.callBackBordersList.callBackGetBorderList();
        makeLineBetweenRows();
        initRecyclerViewBorders();


        return view;
    }

    private void findViewsId(View view) {
        recyclerView = view.findViewById(R.id.RecyclerViewBorders);
    }

    public void initCallBack(CallBackBordersList callBackBordersList) {
        this.callBackBordersList = callBackBordersList;
    }

    private void initRecyclerViewBorders() {
        if (this.borderList != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), context, this.borderList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void makeLineBetweenRows() {
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
    }


}
