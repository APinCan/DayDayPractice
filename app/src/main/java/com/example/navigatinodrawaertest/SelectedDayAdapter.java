package com.example.navigatinodrawaertest;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigatinodrawaertest.Datas.MemoData;

import java.util.ArrayList;

public class SelectedDayAdapter extends RecyclerView.Adapter<SelectedDayAdapter.ViewHolder> {
    ArrayList<MemoData> currentDateMemo = new ArrayList<>();
    String selectedDate;

    private TextView textTitle;
    private TextView textMain;
    private TextView textAddress;
    private TextView textCurrentDay;
    private ImageView memoImageView;
    private Context mContext;

    SelectedDayAdapter(){
    }

    SelectedDayAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context=parent.getContext();
        //layoutinflator를 이용해 memos.xml itemview를 inflate시키고
        //viewholder를 리턴시킴
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memos, parent, false);
        SelectedDayAdapter.ViewHolder vh = new SelectedDayAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MemoData item = currentDateMemo.get(position);

        textTitle.setText(item.getTitle());
        textMain.setText(item.getMain());
        textAddress.setText(item.getAddress());
        textCurrentDay.setText(item.getTextCurrentDay());
        if(item.getMemoBitmap()!=null){
            memoImageView.setImageBitmap(DataConverter.getImage(item.getMemoBitmap()));
            memoImageView.setVisibility(View.VISIBLE);
        }

        memoImageView.invalidate();
    }

    @Override
    public int getItemCount() {
        return currentDateMemo.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(View itemView){
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTitle);
            textMain = itemView.findViewById(R.id.textViewMain);
            textAddress = itemView.findViewById(R.id.textViewAddress);
            textCurrentDay = itemView.findViewById(R.id.textViewCurrentDay);
            memoImageView = itemView.findViewById(R.id.imageViewMemos);
        }
    }

    public void setSelectedDate(ArrayList<MemoData> originMemo,String selectedDate){
        this.selectedDate=selectedDate;
        clearData();

        for(int i=0;i<originMemo.size();i++){
            String day = originMemo.get(i).getTextCurrentDay().split(" ")[0];

            if(day.equals(selectedDate)){
                currentDateMemo.add(originMemo.get(i));
            }
        }
    }

    public void clearData(){
        this.currentDateMemo.clear();
    }
}
