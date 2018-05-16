package com.deepheart.ellecity06.deepheart.net.factory;

import com.deepheart.ellecity06.deepheart.net.response.HttpResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author ellecity06
 * @time 2018/5/9 9:40
 * @des 返回数据预处理
 */
public class ExGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    ExGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;

    }

    /**
     * 进行解析预处理操作
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String value = responseBody.string();

        HttpResponse httpResponse = new HttpResponse();
        try {
            JSONObject response = new JSONObject(value);
            int error = response.getInt("error");
            if (error != 0) {
                httpResponse.setError(error);
                httpResponse.setData(response.getString("data"));
                return (T) gson.fromJson(value, httpResponse.getClass());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gson.fromJson(value, type);
    }
}
