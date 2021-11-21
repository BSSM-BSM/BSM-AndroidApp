package com.zzz2757.bsm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.GetterSetter.GetterSetter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFrag extends Fragment implements View.OnClickListener {
    private Context context;
    private GetterSetter getSet = new GetterSetter();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_login,container,false);
        context = getContext();

        Button login = view.findViewById(R.id.login_btn);
        login.setOnClickListener(this);

        return view;
    }

    private void login(String member_id, String member_pw){
        ApiInterface apiInterface = ApiClient.getApiClient(context).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.login(member_id, member_pw);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(context, getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        Toast.makeText(context, "로그인 성공! status: "+getSet.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(context, "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                EditText edit_member_id = (EditText)getView().findViewById(R.id.member_id);
                EditText edit_member_pw = (EditText)getView().findViewById(R.id.member_pw);
                login(edit_member_id.getText().toString(), edit_member_pw.getText().toString());
                break;
        }
    }
}
