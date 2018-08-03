package com.demo.resetmysoul.study_demo;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.demo.resetmysoul.study_demo.databinding.ActivityMainBinding;
import com.demo.resetmysoul.study_demo.databinding.ShareItemViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements Handler.Callback{

    private Handler handler;
    static final int REQUEST_CODE_MY_PICK = 1;

    @Override
    protected void initView() {
        handler = new Handler(Looper.getMainLooper(), this);

         /* PackageInfo info;
        try{
            info = getPackageManager().getPackageInfo("com.demo.resetmysoul.study_demo", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures)
            {      MessageDigest md;
                md =MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String KeyResult =new String(Base64.encode(md.digest(),0));//String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", KeyResult);
                Log.e("KeyHash:",   Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash2:",   Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Toast.makeText(this,"My FB Key is \n"+ KeyResult , Toast.LENGTH_LONG ).show();
            }
        }catch(PackageManager.NameNotFoundException e1){Log.e("name not found", e1.toString());
        }catch(NoSuchAlgorithmException e){Log.e("no such an algorithm", e.toString());
        }catch(Exception e){Log.e("exception", e.toString());}
*/

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doTransaction() {

    }

    public void testArTime(View view){
        UIUtils.getCurrentDate1();
        UIUtils.getCurrentDate2();
        UIUtils.getCurrentDate3();
//        onOneKeyShare();
//        facebookShare();
//        intentShare();
        //share();
//        showShareItemList();
//        doSocialShare("study_demo" , "ceshiyixia","https://www.baidu.com");
        /*Intent intent = new Intent(this, RecyclerViewTestUI.class);
        startActivity(intent);*/
    }


    private void showShareItemList() {
        Share share = new Share();
        List<ShareItem> shareItems = share.scanShreaApp("http://www.funmart.com");
        for (ShareItem shareItem :
                shareItems) {
            ShareItemViewBinding shareItemViewBinding = ShareItemViewBinding.inflate(getLayoutInflater());
            shareItemViewBinding.ivIcon.setImageDrawable(shareItem.imageIcon);
            shareItemViewBinding.tvName.setText(shareItem.imageLabel);
            mBinding.glyListContent.addView(shareItemViewBinding.getRoot());
        }
    }


    public void doSocialShare(String title, String text, String url){
        // First search for compatible apps with sharing (Intent.ACTION_SEND)
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        // Set title and text to share when the user selects an option.
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Ls.d("name:"+ info.resolvePackageName);
                Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                targetedShare.setType("text/plain"); // put here your mime type
                targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                targetedShareIntents.add(targetedShare);
            }
            // Then show the ACTION_PICK_ACTIVITY to let the user select it

            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0) , UIUtils.getString(R.string.app_name));
            if (chooserIntent == null) {
                return;
            }

            Intent intentPick = new Intent();
            intentPick.setAction(Intent.ACTION_PICK_ACTIVITY);

//            chooserIntent.setAction(Intent.ACTION_PICK_ACTIVITY);

            // Set the title of the dialog000000000000000000000.0
            intentPick.putExtra(Intent.EXTRA_TITLE, title);
            intentPick.putExtra(Intent.EXTRA_INTENT, shareIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS , targetedShareIntents.toArray(new Parcelable[]{}));

            Intent chooser = Intent.createChooser(intentPick, "study_demo");

            //            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            try {
                this.startActivityForResult(intentPick, REQUEST_CODE_MY_PICK);
            } catch (android.content.ActivityNotFoundException ex) {
                UIUtils.showToast("Can't find share component to share");
            }
        }
    }


    public void intentShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                ActivityInfo activityInfo = info.activityInfo;
                String packageName = activityInfo.packageName;
                if (packageName.contains("facebook") || packageName.contains("instagram") || packageName.contains("snapchat") || packageName.contains("whatsapp")) {
                    Ls.d("packageName :" + activityInfo.packageName);
                    targeted.putExtra(Intent.EXTRA_SUBJECT, UIUtils.getString(R.string.app_name));
                    targeted.putExtra(Intent.EXTRA_TEXT, "ceshiceshiceshi");
                    targeted.setPackage(activityInfo.packageName);
                    targetedShareIntents.add(targeted);
                }
            }
            if (ListUtils.getListNoNull(null)) {
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), UIUtils.getString(R.string.app_name));
                if (chooserIntent == null) {
                    return;
                }
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                try {
                    startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    UIUtils.showToast("Can't find share component to share");
                }
            } else {
                intent.putExtra(Intent.EXTRA_SUBJECT, UIUtils.getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, "ceshiceshiceshi");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(Intent.createChooser(intent, UIUtils.getString(R.string.app_name)));
            }

        }
    }

    public void testAnim(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.sort_by_show);
        mBinding.ivAnim.startAnimation(animation);
    }
    /**
     * 一键分享
     */
    private void onOneKeyShare(){
        cn.sharesdk.onekeyshare.OnekeyShare oks = new cn.sharesdk.onekeyshare.OnekeyShare();
        oks.setAddress("12");
        ResourcesManager manager = ResourcesManager.getInstace(this);
        if(!TextUtils.isEmpty(manager.getFilePath())){
            oks.setFilePath(manager.getFilePath());
        } else{
            oks.setFilePath(manager.getFilePath());
        }
        oks.setTitle(manager.getTitle());
        oks.setTitleUrl(manager.getTitleUrl());
        oks.setUrl(manager.getUrl());
        oks.setMusicUrl(manager.getMusicUrl());
        String customText = manager.getText();
        if (customText != null) {
            oks.setText(customText);
        } else if (manager.getText() != null && manager.getText().contains("0")) {
            oks.setText(manager.getText());
        } else {
            oks.setText(this.getString(R.string.share_content));
        }
        oks.setComment(manager.getComment());
        oks.setSite(manager.getSite());
        oks.setSiteUrl(manager.getSiteUrl());
        oks.setVenueName(manager.getVenueName());
        oks.setVenueDescription(manager.getVenueDescription());
        oks.setSilent(true);
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String msg = ResourcesManager.actionToString(i);
                String text = platform.getName() + " completed at " + msg;
                Message message = new Message();
                message.obj = text;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                String msg = ResourcesManager.actionToString(i);
                String text = platform.getName() + "caught error at " + msg;
                Message message = new Message();
                message.obj = text;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                String msg = ResourcesManager.actionToString(i);
                String text = platform.getName() + " canceled at " + msg;
                Message message = new Message();
                message.obj = text;
                handler.sendMessage(message);
            }
        });
        Bitmap logo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        String label = this.getString(R.string.app_name);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                String text = "Customer Logo -- ShareSDK ";
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        };
        oks.setCustomerLogo(logo, label, listener);
        oks.show(this);
    }

    private void facebookShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setText("我是text参数");
        oks.setUrl("http://www.mob.com");
//        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                oks.setCallback(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.d("ShareSDK", "onComplete ---->  分享成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                        Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        Log.d("ShareSDK", "onCancel ---->  分享取消");
                    }
                });

        // 启动分享GUI
        oks.show(MainActivity.this);
    }


    private void share(){
        OnekeyShare oks = new OnekeyShare();
        /*oks.addHiddenPlatform(QQ.NAME

);*/
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if("SinaWeibo".equals(platform.getName())){
                    paramsToShare.setText("我是文本" + "http://sharesdk.cn");
                            paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                }
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle("标题");
                    paramsToShare.setUrl("http://sharesdk.cn");
                            paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
                    /*Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ssdk_logo);
                    paramsToShare.setImageData(imageData);*/
                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);

                }
                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle("标题");
                    paramsToShare.setUrl("http://sharesdk.cn");
                            paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
                if ("QQ".equals(platform.getName())) {
                    paramsToShare.setTitle("标题");
                    paramsToShare.setTitleUrl("http://sharesdk.cn");
                            paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                }
                if("Facebook".equals(platform.getName())){
                    //Facebook空间您自己写了
//                    paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
//                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                    paramsToShare.setUrl("http://www.mob.com");

                    if (!platform.isClientValid()) { //如果客户端不可用
                        paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                        //        oks.setSilent(true);
                    }
                }
                if("QZone".equals(platform.getName())){
                    //QQ空间您自己写了
                }
            }
        });

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareLogin", "onComplete ---->  分享成功");
                platform.getName();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareLogin", "onError ---->  失败" + throwable.getStackTrace().toString());
                Log.d("ShareLogin", "onError ---->  失败" + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d("ShareLogin", "onCancel ---->  分享取消");
            }
        });

        // 启动分享GUI
        oks.show(this);
    }


    @Override
    public boolean handleMessage(Message msg) {
        String toastMsg = (String) msg.obj;
        showToast(toastMsg);
        return false;
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MY_PICK) {
            if(data != null && data.getComponent() != null && !TextUtils.isEmpty(data.getComponent().flattenToShortString()) ) {
                String appName = data.getComponent().flattenToShortString();
                // Now you know the app being picked.
                // data is a copy of your launchIntent with this important extra info added.

                // Start the selected activity
                Ls.d("appName : "+ appName);
                startActivity(data);
            }
        }
    }
}
