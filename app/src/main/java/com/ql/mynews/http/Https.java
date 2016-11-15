package com.ql.mynews.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.ql.mynews.utils.JsonUtils;
import com.ql.mynews.utils.NewsAllBean;
import com.ql.mynews.utils.NewsOtherBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 :QinLi
 * @version 创建时间：2016-9-26 下午3:54:13 类说明
 */
public class Https {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    // KEY
    public static final String APPKEY_NEWS = "04204fc11555bdbba51255485657f688";

    /**
     * 1.新闻总汇总
     *
     * @param context activity
     * @param handler activity利用handler更新界面
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void getRequestForNewsAll(Context context, String type, Handler handler) {
        final Message msg = new Message();
        msg.what = HttpConst.MSG_WHAT_NEWS;
        msg.obj = null;
        NewsAllBean news = new NewsAllBean();
        List<NewsAllBean> list = new ArrayList<NewsAllBean>();
        String result = null;
        String url = "http://v.juhe.cn/toutiao/index";//请求接口地址
        Map params = new HashMap();// 请求参数
        params.put("key", APPKEY_NEWS);//应用APPKEY(应用详细页查询)
        params.put("type", type);//类型

        try {
            result = net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if (object.getString("reason").equalsIgnoreCase("成功的返回")) {// 解析获取数据
                JSONObject secondObj = object.getJSONObject("result");
                JSONArray array = secondObj.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject forBean = (JSONObject) array.get(i);
                    Gson gson = new Gson();
                    news = gson.fromJson(forBean.toString(),NewsAllBean.class);
                    list.add(news);
                }
            } else {
                news = null;
            }
        } catch (Exception e) {
            news = null;
            e.printStackTrace();
        }

        if (news == null) {
            msg.obj = null;
            handler.sendMessage(msg);
        } else {
            msg.obj = list;
            handler.sendMessage(msg);
        }
    }


    /**
     * 1.新闻分支，根据类型获取数据
     *
     * @param context activity
     * @param handler activity利用handler更新界面
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void getRequestForNewsOther(Context context, String type, Handler handler) {
        final Message msg = new Message();
        msg.what = HttpConst.MSG_WHAT_NEWS_TIYU;
        msg.obj = null;
        NewsOtherBean news = new NewsOtherBean();
        List<NewsOtherBean> list = new ArrayList<NewsOtherBean>();
        String result = null;
        String url = "http://v.juhe.cn/toutiao/index";//请求接口地址
        Map params = new HashMap();// 请求参数
        params.put("key", APPKEY_NEWS);//应用APPKEY(应用详细页查询)
        params.put("type", type);//类型

        try {
            result = net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if (object.getString("reason").equalsIgnoreCase("成功的返回")) {// 解析获取数据
                JSONObject secondObj = object.getJSONObject("result");
                JSONArray array = secondObj.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject forBean = (JSONObject) array.get(i);
                    Gson gson = new Gson();
                    news = gson.fromJson(forBean.toString(), NewsOtherBean.class);//将json数据转换成user类实体
                    list.add(news);
                }
            } else {
                news = null;
            }
        } catch (Exception e) {
            news = null;
            e.printStackTrace();
        }

        if (news == null) {
            msg.obj = null;
            handler.sendMessage(msg);
        } else {
            msg.obj = list;
            handler.sendMessage(msg);
        }
    }

    /**--------------------------------以下为共用方法------------------------------
     * -----
     **/
    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method)
            throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(
                            conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    // 将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=")
                        .append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
