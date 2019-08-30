package com.gsls.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.gsls.gt.GT;
import com.gsls.myapplication.annotation.GT_Activity;
import com.gsls.myapplication.annotation.GT_Click;
import com.gsls.myapplication.annotation.GT_Object;
import com.gsls.myapplication.annotation.GT_Res;
import com.gsls.myapplication.annotation.GT_View;
import com.gsls.myapplication.entity.LoginBean;
import com.gsls.myapplication.util.GT_Util;

@GT_Activity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @GT_View(R.id.ioc_tv)
    private TextView tv;

    @GT_View(R.id.ioc_btn)
    private Button btn;

    @GT_Object(valueString = "1079",valueInt = 21,types = {GT_Object.TYPE.STRING, GT_Object.TYPE.INT}, functions = {"setUsername","setAge"})
    private LoginBean loginBean;

    @GT_Res.GT_String(R.string.StringTest)
    private String data;

    @GT_Res.GT_Color(R.color.colorTest)
    private int MyColor;

    @GT_Res.GT_Drawable(R.drawable.ic_launcher_background)
    private Drawable btnBG;

    @GT_Res.GT_Dimen(R.dimen.tv_size)
    private float TextSize;

    @GT_Res.GT_Animation(R.anim.alpha)
    private Animation animation;

    @GT_Res.GT_StringArray(R.array.ctype)
    private String[] strArray;

    @GT_Res.GT_IntArray(R.array.textInt)
    private int[] intArray;

    @GT_Res.GT_Layout(R.layout.activity_main)
    private View layout;

    private static final String TAG = "GT_";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GT_Util.initAll(this);//绑定 Activity
        GT.log_e( "onCreate: tv = " + tv + " btn:" + btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG,"loginBean: " + loginBean);
                Log.i(TAG,"data: " + data);
                Log.i(TAG,"color: " + MyColor);
                Log.i(TAG,"Drawable: " + btnBG);
                Log.i(TAG,"TextSize: " + TextSize);
                Log.i(TAG,"animation: " + animation);
                Log.i(TAG,"strArray: " + strArray);
                Log.i(TAG,"intArray: " + intArray);
                Log.i(TAG,"layout: " + layout);

                tv.setText("实现成功!");
                btn.setTextColor(MyColor);
                btn.setTextSize(TextSize);
                btn.setBackgroundDrawable(btnBG);
                btn.startAnimation(animation);

            }
        });

    }

    @GT_Click({R.id.ioc_btn01,R.id.ioc_btn02,R.id.ioc_btn03})
    public void testBtnOnCLick(View view){
        switch (view.getId()){
            case R.id.ioc_btn01:
                Log.i(TAG,"单击 1 号" );
                break;

            case R.id.ioc_btn02:
                Log.i(TAG,"单击 2 号" );
                break;

            case R.id.ioc_btn03:
                Log.i(TAG,"单击 3 号" );
                break;
        }
    }

}
