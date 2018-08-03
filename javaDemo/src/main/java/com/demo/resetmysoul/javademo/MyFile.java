package com.demo.resetmysoul.javademo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by Mr. Huang on 2018/5/3.
 * Type:
 * des:
 */

public class MyFile {
    public static void main(String[] args) {

        int fourStarCount = 0 ;
        int fiveStarCount = 0;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int rdNum = random.nextInt(100);
            if(rdNum < 95 || fourStarCount >= 1000 * 0.05){
                fiveStarCount++ ;
                stringBuilder.append(5).append("\n");
            } else {
                fourStarCount++;
                stringBuilder.append(4).append("\n");
            }
        }
        stringBuilder.append("fourStarCount:").append(fourStarCount).append(" fiveStarCount:").append(fiveStarCount);

        System.out.print("fourStarCount: "+fourStarCount + " fiveStarCount:"+fiveStarCount);
//        putFile(stringBuilder.toString());
    }

    private static void putFile(String result) {
        byte[] sourceByte = result.getBytes();
        if(null != sourceByte){
            try {
                File file = new File("E:/star.txt");     //文件路径（路径+文件名）
                if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();  //关闭文件输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
