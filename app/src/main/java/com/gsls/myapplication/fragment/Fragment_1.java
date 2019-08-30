package com.gsls.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class Fragment_1 extends Fragment {

    private static final String TAG = "GT_";

    public static Fragment_1 newInstance() {
        Bundle args = new Bundle();
        Log.i(TAG,"Fragment_1 newInstance");
        Fragment_1 fragment = new Fragment_1();
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_1(){
        Log.i(TAG,"Fragment_1");
    }

}
