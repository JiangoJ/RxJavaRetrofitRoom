package com.example.rxjavaretrofit.networking;


import com.example.rxjavaretrofit.models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserAPI {
    @GET("posts")
    Observable<List<User>> getUsers();
}
