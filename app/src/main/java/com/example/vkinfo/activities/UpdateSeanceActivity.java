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

public class UpdateSeanceActivity extends AppCompatActivity {

    private TextView movieId;
    private TextView startTime;
    private TextView day;
    private Button updateSeance;
    private User user;
    private Seance seance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_seance);

        movieId = findViewById(R.id.et_seance_movie_id);
        startTime = findViewById(R.id.et_seance_start_time);
        day = findViewById(R.id.et_seance_day);
        updateSeance = findViewById(R.id.update_seance_btn);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("user")) {
            user = new Gson().fromJson(intentThatStartedThisActivity
                    .getStringExtra("user"), User.class);
        }

        if (intentThatStartedThisActivity.hasExtra("seance")) {
            seance = new Gson().fromJson(intentThatStartedThisActivity
                    .getStringExtra("seance"), Seance.class);
        }

        View.OnClickListener onClickCreateSeanceListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getToken() != null) {
                    updateSeance();
                } else {
                    Toast.makeText(UpdateSeanceActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        updateSeance.setOnClickListener(onClickCreateSeanceListener);
    }

    private void updateSeance() {

        Seance updatedSeance = new Seance(
                Integer.parseInt(movieId.getText().toString()),
                startTime.getText().toString(),
                day.getText().toString());

        Call<ResponseBody> call = getRetrofitUserClient()
                .updateSeance(user.getToken(), String.valueOf(seance.getId()), updatedSeance);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateSeanceActivity.this, "Seance was updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateSeanceActivity.this, "Bad response :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdateSeanceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}