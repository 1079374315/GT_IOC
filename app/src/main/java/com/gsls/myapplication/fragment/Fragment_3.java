package com.gsls.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class Fragment_3 extends Fragment {

    private static final String TAG = "GT_";

    public static Fragment_3 newInstance() {
        Bundle args = new Bundle();
        Log.i(TAG,"Fragment_3 newInstance");
        Fragment_3 fragment = new Fragment_3();
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_3(){
        Log.i(TAG,"Fragment_3");
    }

}
