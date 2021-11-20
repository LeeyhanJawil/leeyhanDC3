package com.example.leeyhanwebmidterm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.HomeActivity;
import com.example.leeyhanwebmidterm.api.RequestPlaceholder;
import com.example.leeyhanwebmidterm.api.RetrofitBuilder;
import com.example.leeyhanwebmidterm.pojos.Login;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public MaterialButton loginbtn;
    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        loginbtn = findViewById(R.id.loginbtn);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);
        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null) {
                    Call<Login> LoginCall = requestPlaceholder.Login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    LoginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (!response.isSuccessful()) {
                                if (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGGING ERR", response.message());
                                } else {
                                    Toast.makeText(LoginActivity.this, "There was an error upon logging in the API", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGGING ERR", response.message());
                                }
                            } else {
                                if (response.code() == 200) {
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", loginResponse.getId());


                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "There was an error upon logging in the API", Toast.LENGTH_SHORT).show();
                            Log.e("LOGGING ERR", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please supply all the fields to login!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}