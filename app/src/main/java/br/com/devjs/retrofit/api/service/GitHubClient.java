package br.com.devjs.retrofit.api.service;

import java.util.List;

import br.com.devjs.retrofit.api.model.GitHubRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by antonios on 14/08/17.
 */

public interface GitHubClient {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);

}
