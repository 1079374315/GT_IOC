package com.gsls.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class Fragment_2 extends Fragment {

    private static final String TAG = "GT_";

    public static Fragment_2 newInstance() {
        Bundle args = new Bundle();
        Log.i(TAG,"Fragment_2 newInstance");
        Fragment_2 fragment = new Fragment_2();
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_2(){
        Log.i(TAG,"Fragment_2");
    }

}
