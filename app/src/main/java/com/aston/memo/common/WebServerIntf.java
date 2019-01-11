package com.aston.memo.common;

import com.aston.memo.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebServerIntf {

    @GET("5c2542a7300000540067f4ec")
    Call<Example> getMyExample();
}
