package com.example.navigatinodrawaertest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
중요!
removeItem이 첫번째 메모가 지워지지 않는 문제

 */

//https://black-jin0427.tistory.com/100
//https://recipes4dev.tistory.com/154
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    //어댑터에 들어갈 list
    private ArrayList<MemoData> memos = new ArrayList<>();
    private Context mContext;
    private TextView textTitle;
    private TextView textMain;
    private Bitmap memoBitmap;
    private TextView textAddress;
    final int MEMO_EDIT = 100;

    private MemoAdapter(){

    }

    private static class MemoAdapterHoler{
        public static final MemoAdapter INSTANCE = new MemoAdapter();
    }

    public static MemoAdapter getInstance(){
        Log.d("ADAPTOR", "getInstance(){");
        return MemoAdapterHoler.INSTANCE;
    }

//    MemoAdapter(Context mContext){
//        this.mContext=mContext;
//    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    //recycler뷰의 핵심인 viewHolder, 여기서 subView를 세팅
    class ViewHolder extends RecyclerView.ViewHolder{
        //뷰홀더를 생성하면서 memos.xml에서 itemview들의 id를 가져옴
        ViewHolder(View itemView) {
            super(itemView);

            textTitle=itemView.findViewById(R.id.textViewTitle);
            textMain=itemView.findViewById(R.id.textViewMain);
            textAddress=itemView.findViewById(R.id.textViewAddress);
        }

        void onBind(MemoData memoData){
            textTitle.setText(memoData.getTitle());
            textMain.setText(memoData.getMain());
            textAddress.setText(memoData.getAddress());
        }
    }

    //아이템뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context=parent.getContext();
        //layoutinflator를 이용해 memos.xml itemview를 inflate시키고
        //viewholder를 리턴시킴
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memos, parent, false);
        MemoAdapter.ViewHolder vh = new MemoAdapter.ViewHolder(view);

        Log.d("ADAPTOR", "onCreateViewHolder");
        Log.d("ADAPTOR", memos.get(i).getTitle());
        Log.d("ADAPTOR", memos.get(i).getMain());

        return vh;
        //return new ViewHolder(view);
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    //아이템을 하나하나 보여주는 능력
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.onBind(memos.get(position));
        //https://www.google.com/search?newwindow=1&ei=FqrWXLOeHe6zmAWTx4DgBg&q=%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C+%EA%B3%B5%EC%9C%A0+%EC%9A%94%EC%86%8C+%EC%A0%84%ED%99%98&oq=%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C+%EA%B3%B5%EC%9C%A0+%EC%9A%94%EC%86%8C+%EC%A0%84%ED%99%98&gs_l=psy-ab.3...104679.109631..109823...6.0..2.164.2148.0j19....2..0....1..gws-wiz.......35i39j0j33i21j33i160.SxUywIRe6ik
        //https://www.youtube.com/watch?v=sv6raw76BRI
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click"+position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, MemoActivity.class);
                intent.putExtra("memoTitle",  memos.get(position).getTitle());
                intent.putExtra("memoMain", memos.get(position).getMain());
                intent.putExtra("memoAddress", memos.get(position).getAddress());

                Log.d("ADAPTOR", "onBindViewHolder");
                Log.d("ADAPTOR", memos.get(position).getTitle());
                Log.d("ADAPTOR", memos.get(position).getMain());

                removeItem(position);
                //하나 넣기
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, v, "memoTransition");

                //mContext.startActivity(intent, options.toBundle());
                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT, options.toBundle());
            }
        });
    }

    //recyclerview의 아이템의 총 개수
    @Override
    public int getItemCount() {
        return memos.size();
    }

    //외부에서 아이템을 추가시켜줌
    public void addItem(MemoData memoData){
        memos.add(memoData);
        Log.d("ADAPTOR", "ADDitem");
    }

    public void removeItem(int position){
        memos.remove(position);
    }

}
