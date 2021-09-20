package com.zzz2757.bsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.Board.BoardActivity;
import com.zzz2757.bsm.GetterSetter.GetterSetter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    GetterSetter getSet = new GetterSetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void  login(String member_id, String member_pw){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.login("login", member_id, member_pw);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        error(getSet.getStatus());
                    }else{
                        Toast.makeText(getApplicationContext(), "로그인 성공! status: "+getSet.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickLogin(View view) {
        EditText edit_member_id = (EditText)findViewById(R.id.member_id);
        EditText edit_member_pw = (EditText)findViewById(R.id.member_pw);
        login(edit_member_id.getText().toString(), edit_member_pw.getText().toString());
    }
    public void onClickBoard(View view){
        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
        startActivity(intent);
    }

    public void error(int error_code){
        Toast.makeText(getApplicationContext(), "에러코드 "+error_code+"\n"+ErrorCode.errorCode(error_code), Toast.LENGTH_SHORT).show();
    }

}