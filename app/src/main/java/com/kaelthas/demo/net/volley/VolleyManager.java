package com.kaelthas.demo.net.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class VolleyManager {

    public Context context = null;
    public RequestQueue mQueue = null;

    private ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

    public String getCacheByKey(String key) {
        String data = null;
        if (cache.containsKey(key)) {
            data = cache.get(key);
        }
        return data;
    }

    public void setCacheByKey(String key, String value) {
        if (cache.containsKey(key)) {
            System.out.println("Warning:the key" + key + "is already exist.");
        }
        cache.put(key, value);
    }

    public VolleyManager(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void load(Request<?> request) {
        mQueue.add(request);
    }

    public void onDestroy() {
        if (mQueue != null) {
            mQueue.stop();
        }
        mQueue = null;
    }

    public ConcurrentMap<String, String> getCache() {
        return cache;
    }

    public void setCache(ConcurrentMap<String, String> cache) {
        this.cache = cache;
    }

}
