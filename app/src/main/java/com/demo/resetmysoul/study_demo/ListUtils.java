package com.demo.resetmysoul.study_demo;

import java.io.IOException;
import java.util.List;

/**
 * author:         luoxiong
 * creation date:  2017/1/23
 * desc ：集合工具类
 */
public class ListUtils {
    /**
     * 判断一个集合非空并且大小大于0
     * @param data
     * @return
     */
    public static boolean getListNoNull(List data) {
        return !(data == null || data.size() < 1);
    }

    /**
     * 深度复制集合
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
//    public static <T> List<T> deepCopy(List<T> src) throws Exception {
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        ObjectOutputStream out = new ObjectOutputStream(byteOut);
//        out.writeObject(src);
//        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
//        ObjectInputStream in = new ObjectInputStream(byteIn);
//        @SuppressWarnings("unchecked")
//        List<T> dest = (List<T>) in.readObject();
//        return dest;
//    }
}
