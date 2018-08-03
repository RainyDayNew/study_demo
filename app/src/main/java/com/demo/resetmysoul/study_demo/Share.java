package com.demo.resetmysoul.study_demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Huang on 2018/3/26.
 * Type:
 * des:
 */

public class Share {


    /**
     * 得到支持分享的应用
     *
     * @return 返回支持分享的app集合
     */
    public List<ShareItem> scanShreaApp(String content) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);

        PackageManager packageManager = UIUtils.getAppContext().getPackageManager();
        List<ResolveInfo> resolveInfos = UIUtils.getAppContext().getPackageManager().queryIntentActivities(shareIntent, 0);
        if(!ListUtils.getListNoNull(resolveInfos)){
            return null;
        }
        ArrayList<ShareItem> shareItems = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            ShareItem shareItem = new ShareItem(resolveInfo.loadLabel(packageManager), resolveInfo.loadIcon(packageManager));
            shareItems.add(shareItem);
        }
        return shareItems;
    }

}
