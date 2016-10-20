package com.kaelthas.demo.net.volley;

import java.util.HashMap;

public class VolleyStringResponse {

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;

    }

    private Object tag;
    private HashMap<String, String> header;
    private String charset;
    private String cookies;
    private String json;

}
