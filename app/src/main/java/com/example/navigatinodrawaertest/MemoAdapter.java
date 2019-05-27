package com.example.navigatinodrawaertest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private TextView textAddress;
    private TextView textCurrentDay;
    private ImageView memoImageView;
    final int MEMO_EDIT = 100;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

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
        databaseHelper=new DatabaseHelper(mContext);

        try{
            db=databaseHelper.getWritableDatabase();
        } catch (SQLException ex){
            db=databaseHelper.getReadableDatabase();
        }
        databaseHelper.setDb(db);
    }

    //recycler뷰의 핵심인 viewHolder, 여기서 subView를 세팅
    class ViewHolder extends RecyclerView.ViewHolder{
        //뷰홀더를 생성하면서 memos.xml에서 itemview들의 id를 가져옴
        ViewHolder(View itemView) {
            //여기도 건드릴꺼없음
            super(itemView);
            textTitle=itemView.findViewById(R.id.textViewTitle);
            textMain=itemView.findViewById(R.id.textViewMain);
            textAddress=itemView.findViewById(R.id.textViewAddress);
            textCurrentDay=itemView.findViewById(R.id.textViewCurrentDay);
            memoImageView=itemView.findViewById(R.id.imageViewMemos);
        }

//        void onBind(MemoData memoData){
//            textTitle.setText(memoData.getTitle());
//            textMain.setText(memoData.getMain());
//            textAddress.setText(memoData.getAddress());
//            textCurrentDay.setText(memoData.getTextCurrentDay());
//            if(memoData.getMemoBitmap()!=null){
//                memoImageView.setImageBitmap(DataConverter.getImage(memoData.getMemoBitmap()));
//                memoImageView.setVisibility(View.VISIBLE);
//            }
//
//        }
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
        //return new ViewHolder(view);
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    //아이템을 하나하나 보여주는 능력
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MemoData item= memos.get(position);


        textTitle.setText(item.getTitle());
        textMain.setText(item.getMain());
        textAddress.setText(item.getAddress());
        textCurrentDay.setText(item.getTextCurrentDay());
        if(item.getMemoBitmap()!=null){
            memoImageView.setImageBitmap(DataConverter.getImage(item.getMemoBitmap()));
            memoImageView.setVisibility(View.VISIBLE);
        }
        Log.d("onBindViewHolder", "뷰출력");
        Log.d("onBindViewHolder", position+"");
        Log.d("onBindViewHolder", item.getId()+"");
        Log.d("onBindViewHolder", memos.size()+"");

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
                intent.putExtra("memoCurrentDay", memos.get(position).getTextCurrentDay());

//                byte[] memoImage=DataConverter.getBytes(memos.get(position).getMemoBitmap());
//                intent.putExtra("memoImage", memoImage);
                intent.putExtra("memoImage", memos.get(position).getMemoBitmap());

                Log.d("ADAPTOR", "onBindViewHolder");
                Log.d("ADAPTOR", memos.get(position).getTitle());
                Log.d("ADAPTOR", memos.get(position).getMain());

                removeItem(position);
                //하나 넣기
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, v, "memoTransition");

                //mContext.startActivity(intent, options.toBundle());
//                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT, options.toBundle());
                ((Activity) mContext).startActivityForResult(intent, MEMO_EDIT);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                removeItem(position);
                notifyDataSetChanged();
                //이메서드에서 이벤트에대한 처리가 끝나서 다른데서 처리할 필요 없으면 true
                //여기서 이벤트 처리를 끝내지 못했을 경우 false
                return true;
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
        //만약 똑같은 데이터가 memos안에 있다면 추가하지 않는다
        if(!memos.contains(memoData)) {
            memos.add(memoData);
        }

        Log.d("ADAPTOR", "ADDitem");
    }

    public void removeItem(int position){
        databaseHelper.deleteEntry(memos.get(position).getId());
        memos.remove(position);
    }

}
