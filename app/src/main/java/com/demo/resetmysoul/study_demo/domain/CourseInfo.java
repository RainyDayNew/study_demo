package com.demo.resetmysoul.study_demo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Huang on 2018/5/16.
 * Type:
 * des:
 */

public class CourseInfo extends BaseInfo {
    public int id;
    public String name;

    public List<ChapterInfo> chapterInfos = new ArrayList<>();
}
