package com.demo.resetmysoul.study_demo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.annotation.Dimension.DP;


/**
 * ================================================
 * 作    者：lx
 * 创建日期：2016/11/23
 * 描    述：UI界面的工具类
 * ================================================
 */
public class UIUtils {

    private static int actionBarSize = 0;


    /**
     * 单例吐司
     */
    public static Toast sToast = null;
    private static long sLastTime = System.currentTimeMillis();

    private static boolean sAppInBackground = false; //应用是否在后台，true表示在后台，false表示在前台

    private UIUtils() {
    }


    public static void showToast(@StringRes int msgId) {
        if (sAppInBackground) {  //如果应用在后台就不弹出Toast提示
            return;
        }
        String msg = UIUtils.getAppContext().getString(msgId);
        showToast(msg);
    }

    @SuppressLint("ShowToast")
    public static void showToast(String msg) {
        if (sAppInBackground) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(UIUtils.getAppContext(), "", Toast.LENGTH_SHORT);
            View view = sToast.getView();
            if (view instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) view;
                if (layout.getChildCount() > 0) {
                    View view1 = layout.getChildAt(0);
                    if (view1 instanceof TextView) {
                        int p8 = UIUtils.dp2px(8);
                        int p5 = UIUtils.dp2px(5);
                        view1.setPadding(p8, p5, p8, p5);
                    }
                }
            }
        }
        if (TextUtils.isEmpty(msg)) return;
        sToast.setText(msg);
        sToast.show();
    }


//    public static Handler getHandler() {
//        return MyApp.sHandler;
//    }


    /**
     * 获取应用程序的上下文
     */

    public static Context getAppContext() {
        return MyApplication.getAppContext();
    }

    /**
     * 获取全局唯一线程池
     *
     * @return 线程池
     */
//    public static ExecutorService getExecutorService() {
//        return MyApp.sExecutorService;
//    }

    /**
     *
     */
//    public static int dp2px(float dp) {
//        float scale = Resources.getSystem().getDisplayMetrics().density;
//        return (int) (dp * scale + 0.5f);
//    }

    /**
     * Converts dps to pixels nicely.
     *
     * @param dp dimension in dps
     * @return dimension in pixels
     * @des dp转换px
     */
    public static int dp2px(@Dimension(unit = DP) float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        try {
            return (int) (dp * metrics.density);
        } catch (NoSuchFieldError ignored) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        }
    }


    /**
     * px转换dp
     */
    public static int px2dp(int px) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /**
     * sp 转 px
     */
    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 将px值转换为sp值
    public static int px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 获取屏幕宽
     */
    private static int sScreenW = 0;
    private static int sScreenH = 0;
    public static int getScreenW() {
        if (sScreenW != 0) return sScreenW;
        if (getAppContext() != null) {
            sScreenW = getAppContext().getResources().getDisplayMetrics().widthPixels;
            return sScreenW;
        }
        sScreenW = new DisplayMetrics().widthPixels;
        return sScreenW;
    }


    /**
     * 获取屏幕高
     */
    public static int getScreenH() {
        if (sScreenH != 0) return sScreenH;
        if (getAppContext() != null) {
            sScreenH=getAppContext().getResources().getDisplayMetrics().heightPixels;
            return sScreenH;
        }
        sScreenH=new DisplayMetrics().heightPixels;
        return sScreenH;
    }




    /**
     * @param view 只能是EditText
     */
    public static void closeSoftInput(final View view, boolean isClose) {
        if (view == null) return;
        final InputMethodManager imm = (InputMethodManager) (getAppContext())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isClose) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            //InputMethodManager.HIDE_NOT_ALWAYS)
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 是否联网
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public static Resources getResource() {
        return getAppContext().getResources();
    }

    public static int getColor(@ColorRes int colorId) {
        return getResource().getColor(colorId);
    }

    public static String getString(@StringRes int resId) {
        if (resId <= 0) return "";
        try {
            return getAppContext().getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取首页列表图片的宽高 ，网格2列
     * <p>
     * 2列：需要减去间距 * 3 — 图片的框0.5dp * 4边 =2dp
     * 3列：需要减去间距 * 4 — 图片的框0.5dp * 6边 =3dp
     *
     * @param column 列数
     * @return 图片的宽高
     */
    public static int getImgWidth2(int column) {
        int interval = UIUtils.dp2px(column == 3 ? 9 : 8);
        return (UIUtils.getScreenW() - interval * (column + 1) - UIUtils.dp2px(column)) / column;
    }

    /**
     * 判断异常是否属于网络异常
     */
    public static boolean isNetError(Throwable e) {
        return e instanceof UnknownHostException || e instanceof SocketException ||
                e instanceof SocketTimeoutException;
    }

    /**
     * 判断异常是否提示了，请重新登录
     */
    private static final String PLEASE_LOGIN = "Please login"; //Please login first!

    public static boolean isAgainLogin(String errorMsg) {
        if (TextUtils.isEmpty(errorMsg)) return false;
        return errorMsg.contains(PLEASE_LOGIN);
    }

    public static void setViewVisibility(View view, boolean showVisibility) {
        if (showVisibility) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 讲按钮至为不可点击状态
     */
//    public static void setBtDisabled(Button bt) {
//        bt.setClickable(false);
//        bt.setBackgroundColor(ContextCompat.getColor(getAppContext(), R.color.grey_ccc));
//        bt.setTextColor(ContextCompat.getColor(getAppContext(), R.color.white));
//    }


    /**
     * 获取版本号
     */
//    public static int getVersionCode() {
//        if (getPackageInfo() == null) {
//            return 0;
//        }
//        return getPackageInfo().versionCode;
//    }

    /**
     * 获取包的信息
     */
    public static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = getAppContext().getPackageManager();
            pi = pm.getPackageInfo(getAppContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断App是否在前台，如果是就弹出一个Dialog，否则在通知栏提示
     */
    public static boolean isForeground() {
        ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
        final String packageName = getAppContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance
                    && packageName.equals(appProcess.processName)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断当前系统是什么语言
     *
     * @return
     */
    public static boolean isRtlForCurrentSys() {
        int layoutDirectionFromLocale = View.LAYOUT_DIRECTION_LTR;
        try {
            layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return layoutDirectionFromLocale != View.LAYOUT_DIRECTION_LTR;
    }


//    /**
//     * 获取反转后的位置，如传进来的是0、1、2、3、4、
//     *
//     * @return 中东4、3、2、1、0
//     */
//    public static int getReversPosition(int pos, int size) {
//        if (shouldReversLayout()) {
//            int newPos = size - 1 - pos;
//            if (newPos < 0) {
//                newPos = 0;
//            } else if (newPos >= size) {
//                newPos = size - 1;
//            }
//            return newPos;
//        }
//        return pos;
//    }



    /**
     * 检测是否在500毫秒内执行了多次，防双击限制
     *
     * @return true，说明是正常点击，false，说明是快速点击了2次
     */
    public static boolean isNoDoubleClick() {
        //防止瞬间点击多次
        long currTime = System.currentTimeMillis();
        if (currTime - sLastTime < 300) {
            sLastTime = currTime;
            return false;
        }
        sLastTime = currTime;
        return true;
    }




    @DrawableRes
    public static int getDrawableRes(@NonNull Context context, @AttrRes int drawable) {
        return getTypedValue(context, drawable).resourceId;
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @AttrRes int color) {
        return getTypedValue(context, color).data;
    }

    @NonNull
    protected static TypedValue getTypedValue(@NonNull Context context, @AttrRes int resId) {
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(resId, tv, true);
        return tv;
    }

    /**
     * Returns screen width.
     *
     * @param context Context to get resources and device specific display metrics
     * @return screen width
     */
    public static int getScreenWidthDP(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }


    /**
     * A convenience method for setting text appearance.
     *
     * @param textView a TextView which textAppearance to modify.
     * @param resId    a style resource for the text appearance.
     */
    @SuppressWarnings("deprecation")
    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(resId);
        } else {
            textView.setTextAppearance(textView.getContext(), resId);
        }
    }


    /**
     * 获取?attr/actionBarSize的 值大小
     */
    public static int getActionBarSize() {
        if (actionBarSize == 0) {
            //获取toolbar的高度
            TypedValue tv = new TypedValue();
            if (MyApplication.getAppContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, MyApplication.getAppContext().getResources().getDisplayMetrics());
                Ls.d("PopupListView", "tv.data=" + tv.data + ",actionBarHeight=" + actionBarSize);
            }
        }


        return actionBarSize;
    }


    public static void setsAppInBackground(boolean appInBackground) {
        sAppInBackground = appInBackground;
    }


    /**
     * 获取当前日期
     */
    public static void getCurrentDate1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Ls.d("Date获取当前日期时间11111:"+simpleDateFormat.format(date));
    }
    /**
     * 获取当前日期
     */
    public static void getCurrentDate2(){
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Ls.d("Date获取当前日期时间222222:"+year+"-"+month+"-"+day);
    }
    /**
     * 获取当前日期
     */
    public static void getCurrentDate3(){
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month+1;
        int day = t.monthDay;
        Ls.d("Date获取当前日期时间333333:"+year+"-"+month+"-"+day);
    }
}
