package com.demo.resetmysoul.study_demo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr. Huang on 2018/3/13.
 * Type:
 * des:
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected B mBinding;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        initView();
        setContentView(mBinding.getRoot());
        doTransaction();
    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void doTransaction();
}
