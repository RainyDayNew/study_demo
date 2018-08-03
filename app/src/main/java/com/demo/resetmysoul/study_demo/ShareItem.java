package com.demo.resetmysoul.study_demo;

import android.graphics.drawable.Drawable;

/**
 * Created by Mr. Huang on 2018/3/26.
 * Type:
 * des:
 */

public class ShareItem {

    public ShareItem(CharSequence imageLabel, Drawable imageIcon) {
        this.imageLabel = imageLabel;
        this.imageIcon = imageIcon;
    }

    public CharSequence imageLabel;
    public Drawable imageIcon;
}
