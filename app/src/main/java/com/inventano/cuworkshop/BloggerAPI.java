package com.inventano.cuworkshop;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerAPI {

    //Key that we will use to retrive post list
    public static final String key = "AIzaSyCiql1eQkMHczkLiS--0HOLxbLJVEoZ2IE";
    public static final String url = "https://www.googleapis.com/blogger/v3/blogs/2836303658339337542/posts/";

    public static PostService postService = null;
    public static PostService getService()
    {
        if(postService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService {
        //This will be used to retrive post list
        //<PostList> type because we have created our main POJO class as PostList which has getPostList method which returns post list
        @GET
        Call<PostList> getPostList(@Url String url);
    }
}
