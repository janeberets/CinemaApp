package com.example.vkinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkinfo.models.Login;
import com.example.vkinfo.models.User;
import com.example.vkinfo.utils.SeancesAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.vkinfo.utils.RetrofitUserClient.getRetrofitUserClient;


public class MainActivity extends AppCompatActivity {

    public EditText usernameFiled;
    public EditText passwordFiled;
    public EditText searchField;
    public Button searchButton;
    public Button loginButton;
    public TextView resultArea;
    public TextView errorMessage;
    public ProgressBar loadingIndicator;
    RecyclerView seancesList;
    SeancesAdapter seancesAdapter;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);
        usernameFiled = findViewById(R.id.et_username);
        passwordFiled = findViewById(R.id.et_password);
        searchButton = findViewById(R.id.search_btn);
        loginButton = findViewById(R.id.login_btn);
        resultArea = findViewById(R.id.tv_result);
        errorMessage = findViewById(R.id.tv_error_message);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        seancesList = findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        seancesList.setLayoutManager(layoutManager);
        seancesList.setHasFixedSize(true);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        };

        View.OnClickListener onClickSearchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getToken() != null) {
                    getSeances();
                } else {
                    Toast.makeText(MainActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginButton.setOnClickListener(onClickListener);
        searchButton.setOnClickListener(onClickSearchListener);
    }


    private void login() {
        Login login = new Login(usernameFiled.getText().toString(), passwordFiled.getText().toString());
        Call<User> call = getRetrofitUserClient().login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                    user.setToken("Bearer " + response.body().getToken());
                } else {
                    Toast.makeText(MainActivity.this, "Login is not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getSeances() {
        Call<ResponseBody> call = getRetrofitUserClient().getAllSeances(user.getToken());

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONArray moviesArray = new JSONArray(response.body().string());
                    List<String> seances = new ArrayList<>();

                    for (int i = 0; i < moviesArray.length(); i++) {
                        StringBuilder seanceInfo = new StringBuilder();

                        seanceInfo
                                .append("Movie title: ")
                                .append(moviesArray.getJSONObject(i).getJSONObject("movie").getString("title"))
                                .append("\n")
                                .append("Movie duration: ")
                                .append(moviesArray.getJSONObject(i).getJSONObject("movie").getString("duration"))
                                .append("\n")
                                .append("Seance start time: ")
                                .append(moviesArray.getJSONObject(i).getString("startTime"))
                                .append("\n")
                                .append("Seance end time: ")
                                .append(moviesArray.getJSONObject(i).getString("endTime"))
                                .append("\n")
                                .append("Day: ")
                                .append(moviesArray.getJSONObject(i).getString("day"))
                                .append("\n");

                        seances.add(seanceInfo.toString());
                    }

                    seancesAdapter = new SeancesAdapter(seances, MainActivity.this);
                    seancesList.setAdapter(seancesAdapter);

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}