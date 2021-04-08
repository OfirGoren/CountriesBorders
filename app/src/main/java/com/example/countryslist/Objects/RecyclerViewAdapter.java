package com.example.countryslist.Objects;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.countryslist.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private final ArrayList<CountryDetails> mData;
    private final ArrayList<CountryDetails> mDataFull;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Activity activity;

    // data is passed into the constructor
    public RecyclerViewAdapter(Activity activity, Context context, ArrayList<CountryDetails> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.activity = activity;
        this.mDataFull = new ArrayList<>(mData);
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        CountryDetails countryDetails = mData.get(position);
        if (countryDetails != null) {
            holder.countyName.setText(countryDetails.getCountryName());
            holder.nativeName.setText(countryDetails.getNativeName());
            Uri myUri = Uri.parse(countryDetails.getFlag());
            Log.i("name", "onBindViewHolder: " + countryDetails.getCountryName() + "\n" + countryDetails.getFlag());
            GlideToVectorYou.justLoadImage(activity, myUri, holder.imageCountry);
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    private final Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CountryDetails> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryDetails item : mDataFull) {
                    if (item.getCountryName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            if (results.values instanceof ArrayList<?>) {
                List<?> result = (List<?>) results.values;
                for (Object object : result) {
                    if (object instanceof CountryDetails) {
                        mData.add((CountryDetails) object); // <-- add to temp
                    }
                }
            }

            notifyDataSetChanged();
        }
    };

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countyName;
        TextView nativeName;
        ImageView imageCountry;

        ViewHolder(View itemView) {
            super(itemView);
            countyName = itemView.findViewById(R.id.countyName);
            nativeName = itemView.findViewById(R.id.nativeName);
            imageCountry = itemView.findViewById(R.id.imageCountry);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    CountryDetails getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}