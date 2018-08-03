package com.demo.resetmysoul.study_demo;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demo.resetmysoul.study_demo.adapter.ChapterAdapter;
import com.demo.resetmysoul.study_demo.adapter.NormalAdapter;
import com.demo.resetmysoul.study_demo.databinding.UiRecyclerTestBinding;
import com.demo.resetmysoul.study_demo.domain.ChapterInfo;
import com.demo.resetmysoul.study_demo.domain.CourseInfo;
import com.demo.resetmysoul.study_demo.domain.SectionInfo;

import java.util.ArrayList;

/**
 * Created by Mr. Huang on 2018/4/2.
 * Type:
 * des:
 */

public class RecyclerViewTestUI extends BaseActivity<UiRecyclerTestBinding> {
    RecyclerView mRecyclerView;
    private NormalAdapter mNormalAdapter;
    private ArrayList<Integer> mArrayList;
    CourseInfo mCourseInfo;

    @Override
    protected void initView() {
        mRecyclerView = mBinding.rclTest;
        initData();
    }

    private void initData() {
        //假数据
        mCourseInfo = new CourseInfo();
        mCourseInfo.name = "假装是课程的名称";
        for(int i=0; i<31; i++){
            ChapterInfo chapterInfo = new ChapterInfo();
            chapterInfo.name = "假装是课时名称"+(i+1);
            chapterInfo.chapterIndex = i;
            if(i==0){
                for(int j=0; j<2; j++){
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第"+(j+1)+"节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            }else if(i==1){
                for(int j=0; j<3; j++){
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第"+(j+1)+"节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            }else if(i==2){

            }else{
                for (int j = 0; j < 9; j++) {
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第" + (j + 1) + "节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            }
            mCourseInfo.chapterInfos.add(chapterInfo);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ui_recycler_test;
    }

    @Override
    protected void doTransaction() {

       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        View headview = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.item_text, mRecyclerView ,false);
        View tv2 = getLayoutInflater().inflate(R.layout.item_text, mRecyclerView ,false);
        TextView tv1 = new TextView(mRecyclerView.getContext());
        tv1.setText("hahahha");
        mNormalAdapter = new NormalAdapter(tv2);
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mArrayList.add(i);
        }
        mNormalAdapter.setArrayList(mArrayList);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(mNormalAdapter);*/

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ChapterAdapter chapterAdapter = new ChapterAdapter(mCourseInfo);
        mRecyclerView.setAdapter(chapterAdapter);
        chapterAdapter.setOnItemClickListener(new ChapterAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, ChapterAdapter.ViewName viewName, int chapterIndex, int sectionIndex) {
                //Timber.v("---onClick---"+viewName+", "+chapterIndex+", "+sectionIndex);
                switch (viewName){
                    case CHAPTER_ITEM:
                        if(mCourseInfo.chapterInfos.get(chapterIndex).sectionInfos.size() > 0){
//                            Timber.v("---onClick---just expand or narrow: "+chapterIndex);
                            if(chapterIndex + 1 == mCourseInfo.chapterInfos.size()){
                                //如果是最后一个，则滚动到展开的最后一个item
                                mRecyclerView.smoothScrollToPosition(chapterAdapter.getItemCount());
//                                Timber.v("---onClick---scroll to bottom");
                            }
                        }else{
                            onClickChapter(chapterIndex);
                        }
                        break;
                    case CHAPTER_ITEM_PRACTISE:
                        onClickPractise(chapterIndex);
                        break;
                    case SECTION_ITEM:
                        onClickSection(chapterIndex, sectionIndex);
                        break;
                }
            }
        });

        //以下是对布局进行控制，让课时占据一行，小节每四个占据一行，结果就是相当于一个ListView嵌套GridView的效果。
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return chapterAdapter.getItemViewType(position) == ChapterAdapter.VIEW_TYPE_CHAPTER ? 4 : 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);



    }

    public void tvClick(View view){
        UIUtils.showToast("ceshiceshiceshi");
        mArrayList.set(0,10100101);
        mNormalAdapter.notifyItemChanged(0);
    }


    private void onClickChapter(int chapterIndex){
        Ls.d("---onClick---play chapter: "+chapterIndex);
        UIUtils.showToast("播放"+chapterIndex);
    }

    private void onClickSection(int chapterIndex, int sectionIndex){
        Ls.d("---onClick---play---section: "+chapterIndex+", "+sectionIndex);
        UIUtils.showToast("播放"+chapterIndex+", "+sectionIndex);
    }

    private void onClickPractise(int chapterIndex){
        Ls.d("---onClick---practise: "+chapterIndex);
    }
}
