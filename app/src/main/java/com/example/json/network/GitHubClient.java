package com.example.json.network;

import com.example.json.pojo.Repo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mamorky on 6/02/18.
 */

public interface GitHubClient {
    @GET("users/{username}/repos")
    Call<ArrayList<Repo>> listRepos(@Path("username") String username);
}
