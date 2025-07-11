package com.example.newsapp.network;

import android.util.Log;

import com.example.newsapp.model.NewsItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApiClient {

    private static final String TAG = "NewsApiClient";
    private static final String API_KEY = "a20981a18a5a495390523b89711b5cad";
    private static final String API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

    public interface NewsCallback {
        void onSuccess(List<NewsItem> newsList);
        void onFailure(Exception e);
    }

    public static void fetchNews(final NewsCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsItem> newsList = new ArrayList<>();
                    try {
                        String json = response.body().string();
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray articles = jsonObject.getJSONArray("articles");

                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject article = articles.getJSONObject(i);

                            String title = article.optString("title");
                            String desc = article.optString("description");
                            String url = article.optString("url");
                            String imageUrl = article.optString("urlToImage");

                            newsList.add(new NewsItem(title, desc, url, imageUrl));
                        }

                        callback.onSuccess(newsList);
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }
        });
    }
}
