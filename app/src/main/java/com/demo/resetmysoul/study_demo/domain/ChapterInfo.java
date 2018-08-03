package com.demo.resetmysoul.study_demo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Huang on 2018/5/16.
 * Type:
 * des:
 */

public class ChapterInfo extends BaseInfo {
    public String name;
    public int chapterIndex;

    public List<SectionInfo> sectionInfos = new ArrayList<>();
}
