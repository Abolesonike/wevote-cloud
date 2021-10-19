package com.fizzy.core.entity;

/**
 * Author FizzyElf
 * Date 2021/10/18 17:06
 * Shiro使用的返回结果类
 */
public class QueryResult {
    private Object data;//封装的具体内容
    private String token;//令牌

    public QueryResult() {
    }

    public QueryResult(Object data, String token) {
        this.data = data;
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
