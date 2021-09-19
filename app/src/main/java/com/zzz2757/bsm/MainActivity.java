package com.zzz2757.bsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    int status;
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
                    if(Integer.parseInt(response.body().getStatus())!=1){
                        errorCode(Integer.parseInt(response.body().getStatus()));
                    }else{
                        Toast.makeText(getApplicationContext(), "로그인 성공! status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  board(){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.board("board", "board");
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    if(Integer.parseInt(response.body().getStatus())!=1){
                        errorCode(Integer.parseInt(response.body().getStatus()));
                    }else{
                        Toast.makeText(getApplicationContext(), "글 불러오기 성공! status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
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
    public void onClickBoard(View view) {
        board();
    }

    public void errorCode(int error_code){
        String[] error_msg = {
                "", "",
                "정상적인 접근이 아닙니다.",
                "로그인 세션 저장에 실패하였습니다.",
                "id 또는 password가 맞지 않습니다.",
                "비밀번호 재입력이 맞지 않습니다.",
                "이미 사용중인 id입니다.",
                "이미 사용중인 닉네임입니다.",
                "계정 인증이 필요합니다.",
                "유효한 코드가 아닙니다.",
                "만료된 코드입니다, 새로운 코드를 발급받아 주세요.",
                "회원가입중 알 수 없는 문제가 발생하였습니다.",
                "수정할 비밀번호 재입력이 맞지 않습니다.",
                "비밀번호 수정에 실패하였습니다.",
                "멤버코드가 없습니다.",
                "검색어가 없습니다.",
                "잘못된 검색 대상입니다.",
                "게시글 번호가 없습니다.",
                "삭제된 게시글 입니다.",
                "정상적인 접근이 아닙니다 로그인 해주세요.",
                "게시글 작성자가 아닙니다.",
                "로그인후 이용 가능 합니다.",
        };
        Toast.makeText(getApplicationContext(), "에러코드 "+error_code+"\n"+error_msg[error_code], Toast.LENGTH_SHORT).show();
    }
}