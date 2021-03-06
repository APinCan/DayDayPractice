package com.example.navigatinodrawaertest.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigatinodrawaertest.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DirectoryFragment extends DialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<String> stringMemoList = new ArrayList<>();

    private int margin=10;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DirectoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DirectoryFragment newInstance(int columnCount) {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

//    bundleFunction(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);

        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mColumnCount=3;

            stringMemoList= findMemodate(getArguments().getStringArrayList("DAY_ARRAY"));
//            stringMemoList=getArguments().getStringArrayList("DAY_ARRAY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(stringMemoList, mListener));
        }
        return view;
    }

    @Override
    public void onResume() {
        int dialogHeight = MainActivity.displayMetrics.heightPixels - (margin * 2) - MainActivity.StatusBarHeight;
        int dialogWidth = MainActivity.displayMetrics.widthPixels - (margin * 2);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        super.onResume();
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (getDialog() == null) {
//            Context context = getContext();
//            while (context instanceof ContextWrapper) {
//                if (context instanceof FragmentActivity) {
//                    break;
//                }
//                context = ((ContextWrapper) context).getBaseContext();
//            }
//            if (context instanceof FragmentActivity) {
//                ((FragmentActivity) context).finish();
//                return;
//            } else {
//                setShowsDialog(false);
//            }
//        }
//
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String date);
    }

    private ArrayList<String> findMemodate(ArrayList<String> tmpList){
        ArrayList<String> returnList = new ArrayList<>();

        for(int i=0;i<tmpList.size();i++){
            if(!returnList.contains(tmpList.get(i))){
                returnList.add(tmpList.get(i));
            }
        }

        return returnList;
    }
}
