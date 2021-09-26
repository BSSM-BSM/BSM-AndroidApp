package com.zzz2757.bsm.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
    PostData getSet = new PostData();
    WebView webView;
    private ArrayList<CommentData> commentList;
    private CommentAdapter commentAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

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
                        postNo_text.setText(""+post_no);
                        postTitle.setText(response.body().getPost_title());
                        memberNickname.setText(response.body().getMember_nickname());
                        postComments.setText(response.body().getPost_comments()+" 댓글");
                        postHit.setText("조회 "+response.body().getPost_hit());
                        postDate.setText(response.body().getPost_date());
                        postLike.setText(response.body().getPost_like());
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

    private void comment(String boardType, int post_no){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.comment("comment", boardType, post_no);
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
                            JSONArray arr_comment = jsonObject.getJSONArray("arr_comment");
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

    private void commentWrite(String boardType, int post_no, String comment, EditText edit_comment){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<GetterSetter> call = apiInterface.commentWrtie("comment_write", boardType, post_no, comment);
        call.enqueue(new Callback<GetterSetter>() {
            @Override
            public void onResponse(Call<GetterSetter> call, Response<GetterSetter> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    getSet.setStatus(response.body().getStatus());;
                    if(getSet.getStatus()!=1){
                        ErrorCode.errorCode(getApplicationContext(), getSet.getStatus());
                    }else{
                        edit_comment.setText(null);
                        commentList = new ArrayList<>();
                        commentAdapter = new CommentAdapter(commentList, getApplicationContext());
                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(commentAdapter);
                        comment(boardType, post_no);
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

    private void likeSend(String boardType, int post_no, int like){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.likeSend("like", boardType, post_no, like);
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
                            postLike.setText(jsonObject.getString("post_like"));
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
}