package com.zzz2757.bsm.Board;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzz2757.bsm.Api.ApiClient;
import com.zzz2757.bsm.Api.ApiInterface;
import com.zzz2757.bsm.ErrorCode;
import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.GetterSetter.GetterSetter;
import com.zzz2757.bsm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardFrag extends Fragment {
    private Context context;
    private GetterSetter getSet = new GetterSetter();
    private ArrayList<BoardData> boardList;
    private BoardAdapter boardAdapter;
    private View view;
    private String boardType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_board,container,false);
        context = getContext();
        boardType = getArguments().getString("boardType");

        boardList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.board_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        boardAdapter = new BoardAdapter(boardList, context);
        recyclerView.setAdapter(boardAdapter);  // Adapter 등록

        board(boardType);

        return view;
    }

    private void board(String boardType){
        ApiInterface apiInterface = ApiClient.getApiClient(context).create(ApiInterface.class);
        Call<String> call = apiInterface.board(boardType);
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
                        ErrorCode.errorCode(context, getSet.getStatus(), getSet.getSubStatus());
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray arr_board = jsonObject.getJSONArray("arrBoard");
                            for(int i=0;i<arr_board.length();i++){
                                JSONObject boardObject = arr_board.getJSONObject(i);
                                boardList.add(new BoardData(
                                        boardObject.getString("boardType"),
                                        Integer.parseInt(boardObject.getString("postNo")),
                                        boardObject.getString("postTitle"),
                                        Integer.parseInt(boardObject.getString("postComments")),
                                        Integer.parseInt(boardObject.getString("memberCode")),
                                        boardObject.getString("memberNickname"),
                                        boardObject.getString("postDate"),
                                        Integer.parseInt(boardObject.getString("postHit")),
                                        Integer.parseInt(boardObject.getString("postLike"))
                                ));
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
                Toast.makeText(context, "서버와 연결에 실패하였습니다.\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
