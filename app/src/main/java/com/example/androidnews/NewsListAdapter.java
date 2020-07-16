package com.example.androidnews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidnews.Util.Global;
import com.example.androidnews.entity.ClassifyListEntity;
import com.example.androidnews.entity.NewsListEntity;

import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private static final String TAG = "NewsListAdapter";
    private List<NewsListEntity> newsList;
    private int indexArea = -1;
    private Context inflater;
    static  public class ViewHolder extends RecyclerView.ViewHolder {
        View navigationView;
        TextView news_textView;
        TextView news_textView_time;
        ImageView news_imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navigationView=itemView;
            news_textView=itemView.findViewById(R.id.news_textView);
            news_imageView=itemView.findViewById(R.id.news_imageView);
            news_textView_time=itemView.findViewById(R.id.news_textView_time);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                NewsListEntity news = newsList.get(position);
                Intent intent=new Intent(v.getContext(),NewInfoActivity.class);
                intent.putExtra("newsId", news.getNewsId());
                v.getContext().startActivity(intent);
                // Toast.makeText(v.getContext(), "你点击了View"+  news.getNewsId(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsListEntity news = newsList.get(position);
        holder.news_textView.setText(news.getNewsTitle());
        holder.news_textView_time.setText(news.getUserName()+"    发布时间："+news.getNewsDate());
        Glide.with(holder.itemView.getContext()).load(news.getNewsPhoto()).into(holder.news_imageView);

        //holder.news_imageView.
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    public NewsListAdapter(List<NewsListEntity> fruitList) {
        //inflater=context;
        newsList = fruitList;
        Log.d(TAG, "NewsListAdapter: newsList"+newsList);
    }
}
