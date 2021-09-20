package com.zzz2757.bsm.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.R;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<BoardData> BoardList;

    public BoardAdapter(ArrayList<BoardData> dataList){
        BoardList = dataList;
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
                    //int index = getAdapterPosition();
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
        holder.post_title.setText(boardData.getPostTitle());
        holder.member_nickname.setText(boardData.getMemberNickname());
        holder.post_date.setText(boardData.getPostDate());
        holder.post_hit.setText(boardData.getPostHit());
    }

    @Override
    public int getItemCount() {
        return BoardList.size();
    }
}
