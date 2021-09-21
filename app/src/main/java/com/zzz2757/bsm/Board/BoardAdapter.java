package com.zzz2757.bsm.Board;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<BoardData> BoardList;
    private Context context;

    public BoardAdapter(ArrayList<BoardData> dataList, Context context){
        this.BoardList = dataList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView post_no, post_title, member_nickname, post_date, post_hit;

        public ViewHolder(View itemView) {
            super(itemView);
            post_no = (TextView) itemView.findViewById(R.id.post_no);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            member_nickname = (TextView) itemView.findViewById(R.id.member_nickname);
            post_date = (TextView) itemView.findViewById(R.id.post_date);
            post_hit = (TextView) itemView.findViewById(R.id.post_hit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        BoardData boardData = BoardList.get(position);
                        Intent intent = new Intent(context, BoardViewActivity.class);
                        intent.putExtra("boardData", boardData);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_board, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BoardData boardData = BoardList.get(position);
        holder.post_no.setText(Integer.toString(boardData.getPostNo()));
        if(boardData.getPostComments()>0){
            holder.post_title.setText(boardData.getPostTitle()+" ["+boardData.getPostComments()+"]");
        }else{
            holder.post_title.setText(boardData.getPostTitle());
        }
        holder.member_nickname.setText(boardData.getMemberNickname());
        holder.post_date.setText(boardData.getPostDate());
        holder.post_hit.setText("조회 "+boardData.getPostHit());
    }

    @Override
    public int getItemCount() {
        return BoardList.size();
    }
}
