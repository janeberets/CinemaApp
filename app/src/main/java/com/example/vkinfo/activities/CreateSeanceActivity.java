package com.example.vkinfo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vkinfo.R;
import com.example.vkinfo.models.Seance;
import com.example.vkinfo.models.User;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.vkinfo.utils.RetrofitUserClient.getRetrofitUserClient;

public class CreateSeanceActivity extends AppCompatActivity {

    private TextView movieId;
    private TextView startTime;
    private TextView day;
    private Button createSeance;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seance);

        movieId = findViewById(R.id.et_seance_movie_id);
        startTime = findViewById(R.id.et_seance_start_time);
        day = findViewById(R.id.et_seance_day);
        createSeance = findViewById(R.id.create_seance_btn);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            user = new Gson().fromJson(intentThatStartedThisActivity
                    .getStringExtra(Intent.EXTRA_TEXT), User.class);
        }

        View.OnClickListener onClickCreateSeanceListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getToken() != null) {
                    createSeance();
                } else {
                    Toast.makeText(CreateSeanceActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        createSeance.setOnClickListener(onClickCreateSeanceListener);
    }

    private void createSeance() {
        Seance seance = new Seance(Integer.parseInt(movieId.getText().toString()),
                startTime.getText().toString(), day.getText().toString());
        Call<ResponseBody> call = getRetrofitUserClient().addSeance(user.getToken(), seance);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateSeanceActivity.this, "Seance was created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateSeanceActivity.this, "Bed response :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateSeanceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}