package org.berendeev.roma.yandexmobilization2017.data.http;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    private CacheControl cacheControl;

    public CacheInterceptor(CacheControl cacheControl) {
        this.cacheControl = cacheControl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request1 = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build();
        return chain.proceed(request1);
    }
}