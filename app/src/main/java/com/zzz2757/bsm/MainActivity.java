package com.zzz2757.bsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.Board.BoardActivity;
import com.zzz2757.bsm.GetterSetter.GetterSetter;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private GetterSetter getSet = new GetterSetter();
    final int versionCode = BuildConfig.VERSION_CODE;
    final String versionName = BuildConfig.VERSION_NAME;

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LoginFrag loginFrag;
    private SettingFrag settingFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_login:
                        setFrag(0);
                        break;
                    case R.id.action_board:
                        setFrag(1);
                        break;
                    case R.id.action_setting:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        loginFrag = new LoginFrag();
        settingFrag = new SettingFrag();
        setFrag(0);

        version();
    }

    private void setFrag(int n){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        switch(n){
            case 0:
                fragmentTransaction.replace(R.id.Main_Frame, loginFrag);
                fragmentTransaction.commit();
                break;
            case 1:
                Intent intent = new Intent(this, BoardActivity.class);
                startActivity(intent);
                break;
            case 2:
                fragmentTransaction.replace(R.id.Main_Frame, settingFrag);
                fragmentTransaction.commit();
                break;
        }
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

}