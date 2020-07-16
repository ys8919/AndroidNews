package com.example.androidnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.androidnews.Util.Global;
import com.example.androidnews.Util.HttpHelper;
import com.example.androidnews.entity.CommentEntity;
import com.example.androidnews.entity.NewsListEntity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class NewInfoActivity extends AppCompatActivity {
    private static final String TAG = "NewInfoActivity";
    private TextView textViewTitle;
    private TextView textViewTime;
    private TextView textInfo;
    private NewsListEntity newsInfo=new NewsListEntity();
    private List<CommentEntity> commentList=new ArrayList<CommentEntity>();
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private Context context=this;
    private TextInputLayout TextComment;
    private EditText CommentText;
    private Button button_comment;
    private int newsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewTime=findViewById(R.id.textViewTime);
        textInfo=findViewById(R.id.textInfo);
        recyclerView=findViewById(R.id.recycler_comment_view);
        TextComment=findViewById(R.id.TextComment);
        CommentText=TextComment.getEditText();
        button_comment=findViewById(R.id.button_comment);



        Intent intent =getIntent();
        newsId =intent.getIntExtra("newsId",-1);

        commentAdapter = new CommentAdapter(commentList);
        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentAdapter);


        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CommentText.getText().toString().equals("")){
                    int userId=Global.userId;
                    if(userId<0){
                        loginDialog();

                        //Toast.makeText(NewInfoActivity.this,"用户未登录", Toast.LENGTH_SHORT).show();
                    }else{
                        TextComment.setErrorEnabled(false);
                        CommentEntity commentEntity=new CommentEntity();
                        commentEntity.setNewsId(newsId);
                        commentEntity.setCommentInfo(CommentText.getText().toString());
                        commentEntity.setUserId(userId);
                        insertComment(commentEntity);
                    }

                }else{
                    TextComment.setError("不能为空");
                    TextComment.setErrorEnabled(true);
                }
            }
        });

        CommentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    //显示错误提示
                    TextComment.setError("不能为空");
                    TextComment.setErrorEnabled(true);
                } else {
                    TextComment.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**
         * 新闻详情
         */
        HashMap<String,Object> news=new HashMap<String,Object>();
        news.put("newsId",newsId);
        final HttpHelper instance1=HttpHelper.getInstance();
        instance1.postDataAsyn(Global.apiUrl+"queryNewsInfo", JSON.toJSONString(news), new HttpHelper.NetCall(){
            @Override
            public void success(Call call, final Response response) throws IOException {

                new Thread(new  Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Log.d(TAG, "run: response:"+response.body().string());
                            final HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});
                            if((Boolean) msg.get("flag")) {
                                String data=msg.get("data").toString();
                                //Log.d(TAG, "run: data"+data);
                                List<NewsListEntity> listClassifyListEntity = JSON.parseArray(data, NewsListEntity.class);
                                newsInfo=listClassifyListEntity.get(0);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewTitle.setText(newsInfo.getNewsTitle());
                                        textViewTime.setText("作者：  "+newsInfo.getUserName()+"    发布时间："+newsInfo.getNewsDate());
                                        textInfo.setText(newsInfo.getNewsInfo());
                                    }
                                });


                            }else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(NewInfoActivity.this,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "run:newsList 信息消失:"+ msg.get("msg").toString());
                                    }
                                });

                            }
                        } catch ( IOException  e ){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewInfoActivity.this,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "run:newsList 服务器请求失败");

                                }
                            });
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            @Override
            public void failed(Call call, final IOException e) {

                runOnUiThread(new  Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context,"服务器请求失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        queryComment();



    }
    /**
     * 查询评论
     */
    void queryComment(){
        HashMap<String,Object> comment=new HashMap<String,Object>();
        comment.put("newsId",newsId);
        comment.put("limit",100);
        comment.put("page",1);
        final HttpHelper instance=HttpHelper.getInstance();
        instance.postDataAsyn(Global.apiUrl+"queryComment", JSON.toJSONString(comment), new HttpHelper.NetCall(){
            @Override
            public void success(Call call, final Response response) throws IOException {

                new Thread(new  Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Log.d(TAG, "run: response: "+response.body().string());
                            final HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});
                            if((Boolean) msg.get("flag")) {
                                String data=msg.get("data").toString();
                                Log.d(TAG, "run: data"+data);
                                List<CommentEntity> CommentEntity = JSON.parseArray(data, CommentEntity.class);
                                commentList.clear();
                                commentList=CommentEntity;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        commentAdapter = new CommentAdapter(commentList);
                                        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(commentAdapter);
                                    }
                                });


                            }else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(NewInfoActivity.this,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "run:newsList 信息消失:"+ msg.get("msg").toString());
                                    }
                                });

                            }
                        } catch ( IOException  e ){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewInfoActivity.this,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "run:newsList 服务器请求失败");

                                }
                            });
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            @Override
            public void failed(Call call, final IOException e) {

                runOnUiThread(new  Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context,"服务器请求失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
    /**
     * 插入评论
     */
    void insertComment(CommentEntity commentEntity){

        HashMap<String,Object> commentInsert=new HashMap<String,Object>();
        commentInsert.put("newsId",newsId);
        commentInsert.put("userId",commentEntity.getUserId());
        commentInsert.put("commentInfo",commentEntity.getCommentInfo());

        final HttpHelper instance2=HttpHelper.getInstance();
        instance2.postDataAsyn(Global.apiUrl+"insertComment", JSON.toJSONString(commentInsert), new HttpHelper.NetCall(){
            @Override
            public void success(Call call, final Response response) throws IOException {

                new Thread(new  Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Log.d(TAG, "run: response:"+response.body().string());
                            final HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});
                            Log.d(TAG, "run: msg"+msg);
                            if((Boolean) msg.get("flag")) {
                                //String data=msg.get("data").toString();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(NewInfoActivity.this,"评论成功", Toast.LENGTH_SHORT).show();
                                        queryComment();
                                    }
                                });

                               /* List<CommentEntity> CommentEntity = JSON.parseArray(data, CommentEntity.class);
                                commentList.clear();
                                commentList=CommentEntity;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        commentAdapter = new CommentAdapter(commentList);
                                        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(commentAdapter);
                                    }
                                });*/


                            }else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(NewInfoActivity.this,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "run:newsList 信息消失:"+ msg.get("msg").toString());
                                    }
                                });

                            }
                        } catch ( IOException  e ){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewInfoActivity.this,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "run:newsList 服务器请求失败");

                                }
                            });
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            @Override
            public void failed(Call call, final IOException e) {

                runOnUiThread(new  Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(context,"服务器请求失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    /**
     * 登录提示弹窗
     */
    void loginDialog(){
        loginDialog alertDialog = new loginDialog.Builder(NewInfoActivity.this)
                .setTitle("未登录请重新登录")
                .setButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(v.getContext(),MainActivity.class);
                        //intent.putExtra("newsId", news.getNewsId());
                        startActivityForResult(intent,Global.PASS_0);
                    }//添加"Yes"按钮

                })
                .create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背景，加这句代码

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.15);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }

    /**
     *
     * 登录回调提示
     * @param bundle
     */
    void loginResultDialog(Bundle bundle){
        loginDialog alertDialog = new loginDialog.Builder(NewInfoActivity.this)
                .setTitle(Objects.requireNonNull(bundle.getString("msg")))
                .setButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }//添加"Yes"按钮

                })
                .create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背景，加这句代码

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.15);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.5);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Log.d(TAG, "onActivityResult: 返回");
        if(requestCode==Global.PASS_0 && resultCode==Global.resultOK){
            Bundle bundle=intent.getExtras();
            if(!bundle.equals("")){

                loginResultDialog(bundle);

            }
        }

    }
}
