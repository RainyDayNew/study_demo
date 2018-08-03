package com.demo.resetmysoul.study_demo.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.demo.resetmysoul.study_demo.MyApplication;
import com.demo.resetmysoul.study_demo.R;

import java.util.ArrayList;

/**
 * Created by Mr. Huang on 2018/4/21.
 * Type:
 * des:
 */

public class BitmapManager {

    private static ArrayList<Bitmap> mBitmapArrayList = new ArrayList<>();

    public static Bitmap getBitmap(int index){
        if (index >= mBitmapArrayList.size()) {
            for (int i = mBitmapArrayList.size(); i <= index ; i++ ) {
                int resId = getDrawableId("icon_rating_star" + i);
                if (resId == 0) {
                    resId = R.drawable.icon_rating_star0;
                }
                Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getAppContext().getResources(), resId);
                mBitmapArrayList.add(bitmap);
            }
        }
        return mBitmapArrayList.get(index);
    }

    private static int getDrawableId(String resName){
        int drawableId = MyApplication.getAppContext().getResources().getIdentifier(resName,
                "drawable", MyApplication.getAppContext().getPackageName());

        return drawableId;
    }
}
