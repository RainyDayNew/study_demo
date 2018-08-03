package com.demo.resetmysoul.study_demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.mob.MobSDK;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mr. Huang on 2018/3/15.
 * Type:
 * des:
 */

public class MyApplication extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        sContext = this;
        facebookHashKey();
    }

    public static Context getAppContext(){
        return sContext;
    }


    //获取hash key
    private void facebookHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.nina.maxnina", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
