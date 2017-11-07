package com.kaelthas.demo.net.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kaelthas.demo.base.AppConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class VolleyStringRequest extends Request<VolleyStringResponse> {

    public final static int GET = Method.GET;
    public final static int POST = Method.POST;

    public final static int PUT = Method.PUT;
    public final static int DELETE = Method.DELETE;
    public final static int HEAD = Method.HEAD;
    public final static int PATCH = Method.PATCH;


    public VolleyStringRequest(int method, String url, Listener<VolleyStringResponse> listener,
                               ErrorListener errorListener) {
        super(method, url, errorListener);
        this.setListener(listener);

        try {
            Map<String, String> _params = super.getParams();
            if (_params != null && _params.keySet().size() > 0) {
                params.putAll(_params);
            }
        } catch (AuthFailureError e) {
            e.printStackTrace();
        }

        try {
            Map<String, String> _headers = super.getHeaders();
            if (_headers != null && _headers.keySet().size() > 0) {
                headers.putAll(super.getHeaders());
            }
        } catch (AuthFailureError e) {
            e.printStackTrace();
        }

    }

    @Override
    protected synchronized Response<VolleyStringResponse> parseNetworkResponse(NetworkResponse response) {
        VolleyStringResponse vResponse = new VolleyStringResponse();
        String parsed;

        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        String rawCookies = response.headers.get("Set-Cookie");
        if (rawCookies == null) {
            rawCookies = response.headers.get("set-cookie");
        }

        vResponse.setCharset(HttpHeaderParser.parseCharset(response.headers));
        vResponse.setCookies("" + rawCookies);
        vResponse.setJson(parsed);
        // 使用当前的TAG进行标识
        vResponse.setTag(this.getTag());

        return Response.success(vResponse, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(VolleyStringResponse response) {
        listener.onResponse(response);
    }

    // 回调事件
    private Listener<VolleyStringResponse> listener = null;
    // 参数
    private HashMap<String, String> params = new HashMap<String, String>();
    // cookies
    private String cookies = "";
    // 请求头
    private HashMap<String, String> headers = new HashMap<String, String>();
    // 请求数据体
    private byte[] paramsBody = null;
    // 超时时间
    private int timeout = AppConfig.HTTP_TIMEOUT;

    public void addParamsBody(byte[] fileBody) {
        paramsBody = fileBody;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public VolleyStringRequest setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    /**
     * @category 追加参数
     */
    public VolleyStringRequest appendParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public String getCookies() {
        return cookies;
    }

    public VolleyStringRequest setCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    /**
     * @category 追加Cookie
     */
    public VolleyStringRequest appendCookie(String cookie) {
        if (!this.cookies.contains(cookie)) {
            this.cookies += cookie;
        }
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public VolleyStringRequest setTimeout(int timeout) {
        this.timeout = timeout;
        // 超时
        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        setRetryPolicy(retryPolicy);
        return this;
    }

    /**
     * @category 追加
     */
    public VolleyStringRequest appendHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public byte[] getParamsBody() {
        return paramsBody;
    }

    public void setParamsBody(byte[] paramsBody) {
        this.paramsBody = paramsBody;
    }

    public Listener<VolleyStringResponse> getListener() {
        return listener;
    }

    public void setListener(Listener<VolleyStringResponse> listener) {
        this.listener = listener;
    }

    // @Override
    // public byte[] getPostBody() throws AuthFailureError {
    // return getParamsBody();
    // }
    //
    // @Override
    // public byte[] getBody() {
    // return getParamsBody();
    // }

    @Override
    protected Map<String, String> getPostParams() throws AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers.put("Cookie", cookies);
        return headers;
    }

}
