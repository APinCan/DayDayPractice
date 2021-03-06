package com.example.navigatinodrawaertest.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigatinodrawaertest.DataConverter;
import com.example.navigatinodrawaertest.Database.DatabaseHelper;
import com.example.navigatinodrawaertest.Datas.MemoData;
import com.example.navigatinodrawaertest.R;

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
    private ArrayList<MemoData> currentDateMemos = new ArrayList<>();

    private Context mContext;
    private TextView textTitle;
    private TextView textMain;
    private TextView textAddress;
    private TextView textCurrentDay;
    private ImageView memoImageView;
    final int MEMO_EDIT = 100;

    private String selectedDate;
    int flag=0;

    public DatabaseHelper databaseHelper;
    public SQLiteDatabase db;

    private MemoAdapter(){
        setHasStableIds(true);
    }

    private static class MemoAdapterHoler{
        public static final MemoAdapter INSTANCE = new MemoAdapter();
    }

    public static MemoAdapter getInstance(){
        Log.d("ADAPTOR", "getInstance(){");
        return MemoAdapterHoler.INSTANCE;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;

        memos.clear();

        databaseHelper=new DatabaseHelper(mContext);

        try{
            db=databaseHelper.getWritableDatabase();
        } catch (SQLException ex){
            db=databaseHelper.getReadableDatabase();
        }
        databaseHelper.setDb(db);
    }

    //아이템뷰를 위한 뷰홀더 객체를 생성하여 리턴
    //건드릴게없음
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context=parent.getContext();
        //layoutinflator를 이용해 memos.xml itemview를 inflate시키고
        //viewholder를 리턴시킴
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memos, parent, false);
        MemoAdapter.ViewHolder vh = new MemoAdapter.ViewHolder(view);

        return vh;
    }

    //recycler뷰의 핵심인 viewHolder, 여기서 subView를 세팅
    class ViewHolder extends RecyclerView.ViewHolder{
        //뷰홀더를 생성하면서 memos.xml에서 itemview들의 id를 가져옴
        ViewHolder(View itemView) {
            //여기도 건드릴꺼없음
            super(itemView);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            textMain = itemView.findViewById(R.id.textViewMain);
            textAddress = itemView.findViewById(R.id.textViewAddress);
            textCurrentDay = itemView.findViewById(R.id.textViewCurrentDay);
            memoImageView = itemView.findViewById(R.id.imageViewMemos);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "click"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(mContext, MemoActivity.class);
//                    intent.putExtra("memoTitle",  memos.get(getAdapterPosition()).getTitle());
//                    intent.putExtra("memoMain", memos.get(getAdapterPosition()).getMain());
//                    intent.putExtra("memoAddress", memos.get(getAdapterPosition()).getAddress());
//                    intent.putExtra("memoCurrentDay", memos.get(getAdapterPosition()).getTextCurrentDay());
//                    intent.putExtra("memoPosition", getAdapterPosition());
//                    Log.d("OnClick", memos.get(getAdapterPosition()).getTitle());
//                    Log.d("OnCLick", "positon:"+getAdapterPosition()+"");
//
//                    intent.putExtra("memoImage", memos.get(getAdapterPosition()).getMemoBitmap());
//
//                    Log.d("ADAPTOR", "onBindViewHolder");
//                    Log.d("ADAPTOR", memos.get(getAdapterPosition()).getTitle());
//                    Log.d("ADAPTOR", memos.get(getAdapterPosition()).getMain());
//
//
//
////                    removeItem(getAdapterPosition());
////                notifyItemChanged(holder.getAdapterPosition(), memos.size());
//                    //하나 넣기
////                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, v, "memoTransition");
//
//                    //mContext.startActivity(intent, options.toBundle());
////                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT, options.toBundle());
//                    ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT);
//                }
//            });
//
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//
//                    Log.d("setOnLongClickListneer", getAdapterPosition()+"");
//                    removeItem(getAdapterPosition());
//
//
//                    notifyDataSetChanged();
//                    //이메서드에서 이벤트에대한 처리가 끝나서 다른데서 처리할 필요 없으면 true
//                    //여기서 이벤트 처리를 끝내지 못했을 경우 false
//                    return true;
//                }
//            });
//        }
        }
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    //아이템을 하나하나 보여주는 능력
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

       final int realPosition=holder.getAdapterPosition();
       MemoData item;


       item = memos.get(realPosition);


        memoImageView.setImageResource(0);
        memoImageView.setVisibility(View.GONE);

        textTitle.setText(item.getTitle());
        textMain.setText(item.getMain());
        textAddress.setText(item.getAddress());
        textCurrentDay.setText(item.getTextCurrentDay());
        if(item.getMemoBitmap()!=null){
            memoImageView.setImageBitmap(DataConverter.getImage(item.getMemoBitmap()));
            memoImageView.setVisibility(View.VISIBLE);
        }

        memoImageView.invalidate();
        Log.d("onBindViewHolder", "뷰출력");
        Log.d("onBindViewHolder", position+"");
        Log.d("onBindViewHolder", realPosition+"");
        Log.d("onBindViewHolder", item.getId()+"");
        Log.d("onBindViewHolder", memos.size()+"");

        for(int i=0;i<memos.size();i++){
            Log.d("onBindViewHolder Title : ", i+"번째 "+memos.get(i).getTitle()+"");
        }

            //https://www.google.com/search?newwindow=1&ei=FqrWXLOeHe6zmAWTx4DgBg&q=%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C+%EA%B3%B5%EC%9C%A0+%EC%9A%94%EC%86%8C+%EC%A0%84%ED%99%98&oq=%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C+%EA%B3%B5%EC%9C%A0+%EC%9A%94%EC%86%8C+%EC%A0%84%ED%99%98&gs_l=psy-ab.3...104679.109631..109823...6.0..2.164.2148.0j19....2..0....1..gws-wiz.......35i39j0j33i21j33i160.SxUywIRe6ik
            //https://www.youtube.com/watch?v=sv6raw76BRI
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "click" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(mContext, MemoActivity.class);
//                intent.putExtra("memoTitle",  memos.get(holder.getAdapterPosition()).getTitle());
//                intent.putExtra("memoMain", memos.get(holder.getAdapterPosition()).getMain());
//                intent.putExtra("memoAddress", memos.get(holder.getAdapterPosition()).getAddress());
//                intent.putExtra("memoCurrentDay", memos.get(holder.getAdapterPosition()).getTextCurrentDay());
//
////                byte[] memoImage=DataConverter.getBytes(memos.get(position).getMemoBitmap());
////                intent.putExtra("memoImage", memoImage);
//                intent.putExtra("memoImage", memos.get(holder.getAdapterPosition()).getMemoBitmap());
//
//                Log.d("ADAPTOR", "onBindViewHolder");
//                Log.d("ADAPTOR", memos.get(position).getTitle());
//                Log.d("ADAPTOR", memos.get(position).getMain());
//
//                removeItem(holder.getAdapterPosition());
////                notifyItemChanged(holder.getAdapterPosition(), memos.size());
//                //하나 넣기
////                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, v, "memoTransition");
//
//                //mContext.startActivity(intent, options.toBundle());
////                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT, options.toBundle());
//                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT);
                    Intent intent = new Intent(mContext, MemoActivity.class);
                    intent.putExtra("memoTitle", memos.get(realPosition).getTitle());
                    intent.putExtra("memoMain", memos.get(realPosition).getMain());
                    intent.putExtra("memoAddress", memos.get(realPosition).getAddress());
                    intent.putExtra("memoCurrentDay", memos.get(realPosition).getTextCurrentDay());
                    intent.putExtra("memoPosition", realPosition);
                    Log.d("OnClick", memos.get(realPosition).getTitle());
                    Log.d("OnCLick", "positon:" + realPosition + "");

                    intent.putExtra("memoImage", memos.get(realPosition).getMemoBitmap());

                    Log.d("ADAPTOR", "onBindViewHolder");
                    Log.d("ADAPTOR", memos.get(realPosition).getTitle());
                    Log.d("ADAPTOR", memos.get(realPosition).getMain());


//                    removeItem(getAdapterPosition());
//                notifyItemChanged(holder.getAdapterPosition(), memos.size());
                    //하나 넣기
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, v, "memoTransition");

                    //mContext.startActivity(intent, options.toBundle());
//                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT, options.toBundle());
                    ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "LongClick : " + realPosition, Toast.LENGTH_SHORT).show();

                    removeItem(realPosition);

                    Log.d("setOnLongClickListneer", holder.getAdapterPosition() + "");
//
//                    notifyDataSetChanged();
                    notifyItemRemoved(realPosition);
                    notifyItemRangeChanged(realPosition, memos.size());
                    //이메서드에서 이벤트에대한 처리가 끝나서 다른데서 처리할 필요 없으면 true
                    //여기서 이벤트 처리를 끝내지 못했을 경우 false
                    return true;
                }
            });
        holder.setIsRecyclable(true);
    }

    //recyclerview의 아이템의 총 개수
    @Override
    public int getItemCount() {
        return memos.size();
    }

    //외부에서 아이템을 추가시켜줌
    public void addItem(MemoData memoData){
        //만약 똑같은 데이터가 memos안에 있다면 추가하지 않는다
        if(!memos.contains(memoData)) {
            memos.add(memoData);
        }

        Log.d("ADAPTOR", "ADDitem");
    }

    public void removeItem(int position){
        databaseHelper.deleteEntry(memos.get(position).getId());
        memos.remove(position);


        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public ArrayList<MemoData> getMemos(){
        return memos;
    }

    public void clearMemos(){
        this.memos.clear();
    }

    public void setMemos(ArrayList<MemoData> memos){
        this.memos=memos;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
