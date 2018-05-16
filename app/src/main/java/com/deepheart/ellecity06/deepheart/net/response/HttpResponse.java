package com.deepheart.ellecity06.deepheart.net.response;

/**
 * @author ellecity06
 * @time 2018/5/9 9:56
 * @des 为了规范 ，与服务器约定的公共的json数据
 */
public class HttpResponse<T> {

    private int error;

    private T data;


    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "error:'" + error + '\'' +
                ", data:" + data +
                '}';
    }
}
