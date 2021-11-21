package com.zzz2757.bsm.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.ErrorCode;
import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.GetterSetter.CommentData;
import com.zzz2757.bsm.GetterSetter.GetterSetter;
import com.zzz2757.bsm.GetterSetter.PostData;
import com.zzz2757.bsm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardViewActivity extends AppCompatActivity {
    private int postNo;
    private String boardType;
    TextView postNo_text, postTitle, memberNickname, postComments, postHit, postDate, postLike;
    Button post_like_btn, post_dislike_btn;
    PostData getSet = new PostData();
    WebView webView;
    private ArrayList<CommentData> commentList;
    private CommentAdapter commentAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    String webviewStyle = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><style>img{width:100%!important;}span{display:inline-block;}</style>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);

        postNo_text = (TextView)findViewById(R.id.post_no);
        postTitle = (TextView)findViewById(R.id.post_title);
        memberNickname = (TextView)findViewById(R.id.member_nickname);
        postComments = (TextView)findViewById(R.id.post_comments);
        postHit = (TextView)findViewById(R.id.post_hit);
        postDate = (TextView)findViewById(R.id.post_date);
        postLike = (TextView) findViewById(R.id.post_like);
        post_like_btn = (Button) findViewById(R.id.post_like_btn);
        post_dislike_btn = (Button) findViewById(R.id.post_dislike_btn);

        webView = (WebView)findViewById(R.id.post_content_webview);
        final WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setSupportMultipleWindows(true);
        webSet.setAllowFileAccessFromFileURLs(true);
        webSet.setDomStorageEnabled(true);
        webSet.setSupportZoom(false);
        webSet.setDefaultTextEncodingName("utf-8");
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                WebSettingsCompat.setForceDark(webSet, WebSettingsCompat.FORCE_DARK_ON);
            }
        }

        Intent intent = getIntent();
        BoardData boardData = (BoardData) intent.getSerializableExtra("boardData");

        commentList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.comment_recycler);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        commentAdapter = new CommentAdapter(commentList, this);
        recyclerView.setAdapter(commentAdapter);  // Adapter 등록

        boardType = boardData.getBoardType();
        postNo = boardData.getPostNo();

        post(boardType, postNo);
        comment(boardType, postNo);
    }

    private void post(String boardType, int postNo){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<PostData> call = apiInterface.post(boardType, postNo);
        call.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        postNo_text.setText(""+postNo);
                        postTitle.setText(response.body().getPostTitle());
                        memberNickname.setText(response.body().getMemberNickname());
                        postComments.setText(response.body().getPostComments()+" 댓글");
                        postHit.setText("조회 "+response.body().getPostHit());
                        postDate.setText(response.body().getPostDate());
                        postLike.setText(response.body().getPostLike());
                        if(response.body().getLike()>0){
                            post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green_btn));
                            post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                        }else if(response.body().getLike()<0){
                            post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                            post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red_btn));
                        }else{
                            post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                            post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                        }
                        webView.loadDataWithBaseURL("https://bssm.kro.kr.", "<!DOCTYPE HTML><html lang=\"kr\"><head>" +webviewStyle+"</head><body>"+response.body().getPostContent()+"</body></html>", "text/html; charset=utf8", "UTF-8", null);
                    }
                }
            }
            @Override
            public void onFailure(Call<PostData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void comment(String boardType, int postNo){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.comment(boardType, postNo);
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
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray arr_comment = jsonObject.getJSONArray("arrComment");
                            for(int i=0;i<arr_comment.length();i++){
                                JSONObject commentObject = arr_comment.getJSONObject(i);
                                commentList.add(new CommentData(
                                        Integer.parseInt(commentObject.getString("memberCode")),
                                        commentObject.getString("memberNickname"),
                                        commentObject.getString("comment"),
                                        commentObject.getString("commentDate")
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        commentAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void commentWrite(String boardType, int postNo, String comment, EditText edit_comment){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.commentWrtie(boardType, postNo, comment);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        edit_comment.setText(null);
                        commentList = new ArrayList<>();
                        commentAdapter = new CommentAdapter(commentList, getApplicationContext());
                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(commentAdapter);
                        comment(boardType, postNo);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetterSetter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickSubmit(View view) {
        EditText edit_comment = (EditText)findViewById(R.id.comment_edit);
        commentWrite(boardType, postNo, edit_comment.getText().toString(), edit_comment);
    }

    private void likeSend(String boardType, int postNo, int like){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.likeSend(boardType, postNo, like);
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
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            postLike.setText(jsonObject.getString("postLike"));
                            if(Integer.parseInt(jsonObject.getString("like"))>0){
                                post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green_btn));
                                post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                            }else if(Integer.parseInt(jsonObject.getString("like"))<0){
                                post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                                post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red_btn));
                            }else{
                                post_like_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                                post_dislike_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
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

    public void onClickPostLike(View view) {
        likeSend(boardType, postNo, 1);
    }

    public void onClickPostDislike(View view) {
        likeSend(boardType, postNo, -1);
    }

    public void onClickPostModify(View view) {
        Intent intent = new Intent(this, PostWriteActivity.class);
        intent.putExtra("boardType", boardType);
        intent.putExtra("postNo", postNo);
        this.startActivity(intent);
    }
}