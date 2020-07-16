package com.example.androidnews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnews.entity.CommentEntity;
import com.example.androidnews.entity.NewsListEntity;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private static final String TAG = "CommentAdapter";
    private List<CommentEntity> commentList;
    private int indexArea = -1;
    static  public class ViewHolder extends RecyclerView.ViewHolder {
        View navigationView;
        TextView comment_textView_user;
        TextView comment_textView;
        TextView comment_textView_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navigationView=itemView;
            comment_textView_user=itemView.findViewById(R.id.comment_textView_user);
            comment_textView=itemView.findViewById(R.id.comment_textView);
            comment_textView_time=itemView.findViewById(R.id.comment_textView_time);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        CommentEntity news = commentList.get(position);
        holder.comment_textView_user.setText("用户: "+news.getUserName());
        holder.comment_textView.setText(news.getCommentInfo());
        holder.comment_textView_time.setText(news.getCommentDate());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
    public CommentAdapter(List<CommentEntity> fruitList) {
        //inflater=context;
        commentList = fruitList;
        Log.d(TAG, "NewsListAdapter: newsList"+commentList);
    }
}
