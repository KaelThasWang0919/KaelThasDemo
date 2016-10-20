package com.kaelthas.demo.net.volley;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @category 数据适配器，提供JSON与BEAN的互相转化
 */
public class DataConvert<T> {

    private String json = null;
    private T bean = null;
    private List<T> list = null;

    public DataConvert(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public T getBean(Type type) {
        if (bean == null) {
            try {

                // Type mySuperClass = this.getClass().getGenericSuperclass();
                // Type _type = ((ParameterizedType) mySuperClass)
                // .getActualTypeArguments()[0];
                //
                // System.out.println("get my type :" + _type);

                JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
                bean = new Gson().fromJson(obj, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public List<T> getList(Class<T> clazz) {
        if (list == null) {
            try {
                JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(new Gson().fromJson(elem, clazz));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String toJson() {
        String json = "";
        try {
            json = new Gson().toJson(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public String toJsonList() {
        String json = "";
        try {
            json = new Gson().toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
