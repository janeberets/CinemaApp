package com.example.vkinfo.utils;

import com.example.vkinfo.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.vkinfo.utils.RetrofitUserClient.getRetrofitUserClient;

public class NetworkTask extends BaseTask {

    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    private final User user;

    public NetworkTask(iOnDataFetched onDataFetchedListener, User user) {
        this.listener = onDataFetchedListener;
        this.user = user;
    }

    @Override
    public Object call() throws Exception {

        Object result = null;
        result = someNetworkFunction();//some network request for example
        return result;
    }

    private Object someNetworkFunction() {
        Call<ResponseBody> call = getRetrofitUserClient().getAllSeances(user.getToken());
        final StringBuilder allSeancesList = new StringBuilder();

        call.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONArray moviesArray = new JSONArray(response.body().string());

                    for (int i = 0; i < moviesArray.length(); i++) {
                        allSeancesList.append(moviesArray.getJSONObject(i)
                                .getJSONObject("movie").getString("title")).append("\n");
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

        return allSeancesList;
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.setDataInPageWithResult(result);
        listener.hideProgressBar();
    }
}

