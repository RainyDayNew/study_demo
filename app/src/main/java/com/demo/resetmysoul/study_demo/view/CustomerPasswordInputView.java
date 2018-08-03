package com.demo.resetmysoul.study_demo.view;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.demo.resetmysoul.study_demo.Ls;
import com.demo.resetmysoul.study_demo.databinding.ViewCustomerPasswordInputBinding;


/**
 * Created by Mr. Huang on 2018/8/2.
 * Type:
 * des:
 */

public class CustomerPasswordInputView extends FrameLayout {

    public String mPasswordText = "";


    /**
     * 控制是否明文显示密码
     */
    public ObservableBoolean mShowPasswrodContent = new ObservableBoolean(true);

    /**
     * 控制清除按钮的展示
     */
    public ObservableBoolean mShowIBClear = new ObservableBoolean(false);

    private EditText mEtPw;


    public CustomerPasswordInputView(Context context) {
        super(context);
        initView(context);
    }

    public CustomerPasswordInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomerPasswordInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        ViewCustomerPasswordInputBinding binding = ViewCustomerPasswordInputBinding.inflate(LayoutInflater.from(context), this, true);
        binding.setModel(this);

        mEtPw = binding.etPw;

        mEtPw.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mShowIBClear.set(mPasswordText.length() > 0);
                } else {
                    mShowIBClear.set(false);
                }
            }
        });
        mEtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Ls.d("TextWatcher 文本变化 beforeTextChanged" + s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ls.d("TextWatcher 文本变化 onTextChanged" + s);
                if (s.length() > 0 && !mShowIBClear.get()) {
                    mShowIBClear.set(true);
                } else if(s.length() == 0){
                    mShowIBClear.set(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Ls.d("TextWatcher 文本变化 afterTextChanged" + s);

            }
        });

        setEditTextInhibitInputSpace(binding.etPw);

    }


    /**
     * 禁止EditText输入空格
     */
    public void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" ") || dend >= 20){
                    return "";
                } else {
                    return null;
                }
            }
        };
        InputFilter[] inputFilters = {filter};
        editText.setFilters(inputFilters);
    }



    public void showHidePassword(View view){
        mEtPw.setTransformationMethod(mShowPasswrodContent.get() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        mShowPasswrodContent.set(!mShowPasswrodContent.get());
        mEtPw.setSelection(mPasswordText.length());

    }

    public void onClearTvClick(View view){
        mPasswordText = "";
        mEtPw.setText("");
    }

}
