package com.zzz2757.bsm.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.ErrorCode;
import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    BoardData getSet = new BoardData("", 0, "", 0, 0, "", "", 0, 0);
    private ArrayList<BoardData> boardList;
    BoardAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        boardList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.board_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        boardAdapter = new BoardAdapter(boardList, this);
        recyclerView.setAdapter(boardAdapter);  // Adapter 등록

        board();
    }

    private void board(){
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        Call<String> call = apiInterface.board("board", "board");
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
                        //Toast.makeText(getApplicationContext(), "글 불러오기 성공! status: "+getSet.getStatus(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray arr_board = jsonObject.getJSONArray("arr_board");
                            for(int i = 0; i < arr_board.length(); i++){
                                JSONObject boardObject = arr_board.getJSONObject(i);
                                getSet.setBoardType(boardObject.getString("boardType"));
                                getSet.setPostNo(Integer.parseInt(boardObject.getString("postNo")));
                                getSet.setPostTitle(boardObject.getString("postTitle"));
                                getSet.setPostComments(Integer.parseInt(boardObject.getString("postComments")));
                                getSet.setMemberCode(Integer.parseInt(boardObject.getString("memberCode")));
                                getSet.setMemberNickname(boardObject.getString("memberNickname"));
                                getSet.setPostDate(boardObject.getString("postDate"));
                                getSet.setPostHit(Integer.parseInt(boardObject.getString("postHit")));
                                getSet.setPostLike(Integer.parseInt(boardObject.getString("postLike")));
                                boardList.add(new BoardData(getSet.getBoardType(), getSet.getPostNo(), getSet.getPostTitle(), getSet.getPostComments(), getSet.getMemberCode(), getSet.getMemberNickname(), getSet.getPostDate(), getSet.getPostHit(), getSet.getPostLike()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        boardAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
