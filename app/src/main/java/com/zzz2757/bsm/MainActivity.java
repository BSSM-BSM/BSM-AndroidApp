package com.zzz2757.bsm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.Board.BoardActivity;
import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.GetterSetter.GetterSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    GetterSetter getSet = new GetterSetter();
    final int versionCode = BuildConfig.VERSION_CODE;
    final String versionName = BuildConfig.VERSION_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        version();
    }

    private void version(){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.version("version", "android", "app");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        getSet.setStatus(Integer.parseInt(jsonObject.getString("status")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus());
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            if(versionCode < Integer.parseInt(jsonObject.getString("versionCode"))){
                                newVersion(jsonObject.getString("versionName"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void newVersion(String newVersionName){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최신 버전이 있습니다")
                .setMessage("현재버전: "+versionName+"\n최신버전: "+newVersionName);
        builder.setPositiveButton("업데이트", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bssm.kro.kr/download?os=android&app=app"));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) { }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void login(String member_id, String member_pw){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.login("login", member_id, member_pw);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus());
                    }else{
                        Toast.makeText(getApplicationContext(), "로그인 성공! status: "+getSet.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

}