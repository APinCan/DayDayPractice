package com.example.navigatinodrawaertest.activities;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigatinodrawaertest.Datas.MemoData;
import com.example.navigatinodrawaertest.R;
import com.example.navigatinodrawaertest.activities.DirectoryFragment.OnListFragmentInteractionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    ArrayList<String> directoryDate = new ArrayList<>();
//    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<String> items, OnListFragmentInteractionListener listener) {
        directoryDate=items;
//        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = mValues.get(position);
        holder.directoryTextView.setText(directoryDate.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.d("onBindViewHolder", "postion : "+position);
                    mListener.onListFragmentInteraction(directoryDate.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return directoryDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView folderImageView;
        public final TextView directoryTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            folderImageView = (ImageView) view.findViewById(R.id.imageViewFolder);
            directoryTextView = (TextView) view.findViewById(R.id.directoryTextView);
        }
    }

    public void makeArray(ArrayList<MemoData> memoData){
        for(int i=0;i<memoData.size();i++){
            String day=memoData.get(i).getTextCurrentDay();
            String[] days=day.split(" ");

            SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date date  = format.parse(days[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
