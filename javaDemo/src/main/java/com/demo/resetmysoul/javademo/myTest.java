package com.demo.resetmysoul.javademo;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


/**
 *     javaDemo.iml 文件
 *     <orderEntry type="library" exported="" scope="" name="gson-2.8.2" level="project" />
 */

public class myTest {


    public static void main(String[] args) {

        testPrint();

    }

    private static void testPrint() {
        Gson mGson= new Gson();
        ArrayList<String> data = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(random.nextInt()));
        }

        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < data.size(); i++) {
//            jsonArray.add(data.get(i));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id",12365);
            jsonObject.addProperty("quantity", 2);
            jsonObject.addProperty("item_price", 56);
            jsonArray.add(jsonObject);
        }
        String goodsIdsValue = mGson.toJson(jsonArray);

        System.out.println("Gson.toJson:" + goodsIdsValue);
        System.out.println("json.tostring:" + jsonArray.toString());
    }


}
