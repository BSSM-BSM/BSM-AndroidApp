package com.zzz2757.bsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String homeUrl = "bssm.kro.kr";
        Intent intent = getIntent();
        status = intent.getStringExtra("status");

        getStatus(status);
    }

    private void  getStatus(String test){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.getTest(test);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    Toast.makeText(getApplicationContext(), "status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}