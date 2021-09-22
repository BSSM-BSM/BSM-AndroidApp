package com.zzz2757.bsm.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zzz2757.bsm.GetterSetter.CommentData;
import com.zzz2757.bsm.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentData> CommentList;
    private Context context;

    public CommentAdapter(ArrayList<CommentData> dataList, Context context){
        this.CommentList = dataList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView member_nickname, comment, comment_date;

        public ViewHolder(View itemView) {
            super(itemView);
            member_nickname = (TextView) itemView.findViewById(R.id.member_nickname);
            comment = (TextView) itemView.findViewById(R.id.comment);
            member_nickname = (TextView) itemView.findViewById(R.id.member_nickname);
            comment_date = (TextView) itemView.findViewById(R.id.comment_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {}
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CommentData commentData = CommentList.get(position);
        holder.member_nickname.setText(commentData.getMemberNickname());
        holder.comment.setText(commentData.getComment());
        holder.comment_date.setText(commentData.getCommentDate());
    }

    @Override
    public int getItemCount() {
        return CommentList.size();
    }
}
