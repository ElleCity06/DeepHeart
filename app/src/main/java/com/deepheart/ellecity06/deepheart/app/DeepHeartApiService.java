package com.deepheart.ellecity06.deepheart.app;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author ellecity06
 * @time 2018/5/8 9:55
 * @packge name：com.example.ellecity06.httptest
 * @name HttpTest
 * @des TODO retrofit接口
 */

public interface DeepHeartApiService {

    // 用户登录
    @POST("")
    Observable<String> userLogin(@Body RequestBody body);
}
