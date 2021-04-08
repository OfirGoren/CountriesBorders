package com.example.countryslist.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.countryslist.CallBacks.CallBackBordersList;
import com.example.countryslist.Fragments.BordersFragment;
import com.example.countryslist.Fragments.CountryFragment;
import com.example.countryslist.Objects.CountryDetails;
import com.example.countryslist.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallBackBordersList {

    private ArrayList<CountryDetails> mBorderList;
    private BordersFragment bordersFragment;
    private CountryFragment countryFragment;
    private MaterialToolbar toolBar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsId();

        setSupportActionBar(toolBar);

        mBorderList = new ArrayList<>();
        //initialize fragments
        initFragments();

        showFragment(countryFragment);
        toolBar.setNavigationOnClickListener(pressNarrowBack);

        //callbacks
        countryFragment.initCallBack(this);
        bordersFragment.initCallBack(this);

    }

    private void initFragments() {
        countryFragment = new CountryFragment();
        bordersFragment = new BordersFragment();
    }

    private void findViewsId() {
        toolBar = findViewById(R.id.toolBar);
        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout);
    }


    View.OnClickListener pressNarrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setDisplayNarrowEnabled(false);
            collapsingToolbarLayout.setTitle(getString(R.string.listCountries));
            removeFragment();

        }
    };

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        setDisplayNarrowEnabled(false);
        //when remain only one Fragment (CountryFragment)
        if (count == 1) {
            finish();
            //when we in second Fragment
        } else {
            //change the title
            collapsingToolbarLayout.setTitle(getString(R.string.listCountries));
            //remove the current fragment
            removeFragment();
        }

    }

    private void setDisplayNarrowEnabled(Boolean value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(value);
        }
    }

    private void removeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();

    }

    private void showFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.frameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
        closeKeyboard();

    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void displayHideFragments(Fragment displayFragment, Fragment hideFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // if the fragment is already in container
        if (displayFragment.isAdded()) {
            ft.show(displayFragment);
            // fragment needs to be added to frame container
        } else {
            ft.add(R.id.frameLayout, displayFragment)
                    .addToBackStack(null);
        }
        if (hideFragment.isAdded()) {
            //hide
            ft.hide(hideFragment);


        }
        ft.commit();

    }

    @Override
    public void CallBackBorderList(ArrayList<CountryDetails> borderList) {
        mBorderList = borderList;
        // show the narrow go back
        setDisplayNarrowEnabled(true);
        collapsingToolbarLayout.setTitle(getString(R.string.borders));
        // change fragment
        displayHideFragments(bordersFragment, countryFragment);


    }

    @Override
    public ArrayList<CountryDetails> callBackGetBorderList() {
        return mBorderList;
    }

}