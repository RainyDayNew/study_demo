package com.demo.resetmysoul.javademo;


import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *     javaDemo.iml 文件
 *     <orderEntry type="library" exported="" scope="" name="gson-2.8.2" level="project" />
 */

public class myClass {
    public final static String AUTH_KEY_FCM= "AAAAXKIeLRM:APA91bEncY_UbZWXpXE_x5UEb3EUKWejiDKRqExvNymfznh8l2FTFkNhGhR9UradF9MF3yKbBgbbsm6WXhgS6FGIxkXBxiguqSW47Hif4nhbpc14yDfs8u4MwrMro0K0ngk6_NsFTAP8";//app服务密钥

    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";//谷歌推送地址


    public static void main(String[] args) {
        String content = "{\"to\" : \"cphS-8kx_DY:APA91bElFftYMk1I4sJ7lGB2bFQz2TwR4AdQuH4yI3j6wTTV53HAkC9PcQOsfwJ2UVd75JULk6i2wLjjqUd5Z9S1JxNBULUaIXKai9BcePXtPAPhSvvmnaUaLFM3PUBckMM5VN4dJ8EE\",\"notification\" : {\"body\" : \"great match!\",\"title\" : \"Portugal vs. Denmark\",\"icon\" : \"myicon\"}}";
        pushFCMNotification(content);
    }


    public static void pushFCMNotification(String content) {
        try {
            URL url = new URL(API_URL_FCM);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");//不设置默认发送文本格式。设置就是json
            conn.setRequestProperty("Authorization","key="+AUTH_KEY_FCM);//不设置默认发送文本格式。设置就是json
            JsonObject json = new JsonObject();
            json.addProperty("to","d4wE2xuFb2o:APA91bErY2pqeb0nQ4Mr25c0QOzEqYRo328RAyol06ZQLYQwp8EJvW8TQGg0btdrI4DpbWALhs4tFO9I-WULrocvA4ibl5_vFFW29d4gsQb95nISFqyv0aB6JvDa5o75wB3PG5MV-dxd");//推送到哪台客户端机器
            JsonObject info = new JsonObject();
            info.addProperty("title","Notification Tilte");
            info.addProperty("body", "Hello Test notification");
            info.addProperty("icon", "myicon");
            json.add("notification", info);//json 还可以put其他你想要的参数

/*            JsonObject data = new JsonObject();
            data.addProperty("action","product_review");
            data.addProperty("bundle","heiheihei");

            json.add("data",data);*/
            System.out.println(json.toString());

  /*          OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();*/

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(json.toString());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            reader.close();
            conn.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
