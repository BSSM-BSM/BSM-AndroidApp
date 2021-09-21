package com.zzz2757.bsm.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.ErrorCode;
import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.GetterSetter.PostData;
import com.zzz2757.bsm.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardViewActivity extends AppCompatActivity {
    TextView postTitle, memberNickname, postComments, postHit, postDate;
    PostData getSet = new PostData();
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);

        postTitle = (TextView)findViewById(R.id.post_title);
        memberNickname = (TextView)findViewById(R.id.member_nickname);
        postComments = (TextView)findViewById(R.id.post_comments);
        postHit = (TextView)findViewById(R.id.post_hit);
        postDate = (TextView)findViewById(R.id.post_date);

        webView = (WebView)findViewById(R.id.post_content_webview);
        final WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setSupportMultipleWindows(true);
        webSet.setAllowFileAccessFromFileURLs(true);
        webSet.setDomStorageEnabled(true);
        webSet.setSupportZoom(true);
        webSet.setBuiltInZoomControls(true);
        webSet.setDisplayZoomControls(false);
        webSet.setDefaultTextEncodingName("utf-8");

        Intent intent = getIntent();
        BoardData boardData = (BoardData) intent.getSerializableExtra("boardData");
        post("board", boardData.getPostNo());
    }

    private void post(String boardType, int post_no){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<PostData> call = apiInterface.post("post", boardType, post_no);
        call.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus());
                    }else{
                        postTitle.setText(response.body().getPost_title());
                        memberNickname.setText(response.body().getMember_nickname());
                        postComments.setText(response.body().getPost_comments()+" 댓글");
                        postHit.setText("조회 "+response.body().getPost_hit());
                        postDate.setText(response.body().getPost_date());
                        webView.loadDataWithBaseURL("https://bssm.kro.kr.", response.body().getPost_content(), "text/html; charset=utf8", "UTF-8", null);
                    }
                }
            }
            @Override
            public void onFailure(Call<PostData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}