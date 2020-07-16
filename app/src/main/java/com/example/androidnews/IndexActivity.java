package com.example.androidnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.example.androidnews.Util.Global;
import com.example.androidnews.Util.HttpHelper;
import com.example.androidnews.entity.ClassifyListEntity;
import com.example.androidnews.entity.NewsListEntity;
import com.example.androidnews.entity.UsersEntity;
import com.example.androidnews.ui.home.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private Context context=this;
    private List<ClassifyListEntity> ClassifyList = new ArrayList<ClassifyListEntity>();
    private List<NewsListEntity> newsList = new ArrayList<NewsListEntity>();
    private  HomeViewModel userModel;
    private TextView text_userNameT;
    private TextView text_EmailT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        userModel = ViewModelProviders.of(IndexActivity.this).get(HomeViewModel.class);     //实例化vm类


       /* UsersEntity user=new UsersEntity();
        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());*/
        ClassifyListEntity classifyList=new ClassifyListEntity();
        /**
         * 获取新闻标题
         */
        final HttpHelper instance=HttpHelper.getInstance();
        instance.postDataAsyn(Global.apiUrl+"queryAllClassifyList", JSON.toJSONString(classifyList), new HttpHelper.NetCall(){

            @Override
            public void success(Call call, final Response response) throws IOException {

                runOnUiThread(new  Runnable() {
                    @Override
                    public void run() {

                        try {
                            HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});

                             if((Boolean) msg.get("flag")) {
                                 String data=msg.get("data").toString();
                                 List<ClassifyListEntity> listClassifyListEntity = JSON.parseArray(data, ClassifyListEntity.class);
                                ClassifyList=listClassifyListEntity;


                            userModel.setText(ClassifyList);
                            Log.d(TAG, "run: ClassifyList" + ClassifyList);
                            }else
                            {
                                Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "run: 服务器请求失败:"+ msg.get("msg").toString());
                            }
                        } catch (JSONException e){
                            Toast.makeText(context,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "run: 服务器请求失败");
                        } catch (IOException | NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                });
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


        /**
         * 获取新闻列表
         */
        HashMap<String,Object> news=new HashMap<String,Object>();
        news.put("limit",100);
        news.put("page",1);
        final HttpHelper instance1=HttpHelper.getInstance();
        instance1.postDataAsyn(Global.apiUrl+"queryNewsList", JSON.toJSONString(news), new HttpHelper.NetCall(){
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
                                List<NewsListEntity> listClassifyListEntity = JSON.parseArray(data, NewsListEntity.class);
                                newsList.clear();
                                newsList=listClassifyListEntity;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        userModel.setTextNews(newsList);
                                        userModel.setCountPage((Integer) msg.get("count"));
                                    }
                                });

                                Log.d(TAG, "run: newsList" + newsList);
                            }else
                            {
                                Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "run:newsList 服务器请求失败:"+ msg.get("msg").toString());
                            }
                        } catch (JSONException | IOException | NullPointerException e ){
                            Toast.makeText(context,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "run:newsList 服务器请求失败");
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


         /*userModel.ControlGlobalRefresh.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(final Integer integer) {
                if(integer>=1){
                    Log.d(TAG, "onChanged: ControlGlobalRefresh="+integer);
                    HashMap<String,Object> news=new HashMap<String,Object>();
                    news.put("limit",Global.limit);
                    news.put("page",integer);
                    final HttpHelper instance1=HttpHelper.getInstance();
                    instance1.postDataAsyn(Global.apiUrl+"queryNewsList", JSON.toJSONString(news), new HttpHelper.NetCall(){
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
                                            List<NewsListEntity> listClassifyListEntity = JSON.parseArray(data, NewsListEntity.class);
                                            if(integer==1){
                                                newsList.clear();
                                                newsList=listClassifyListEntity;
                                            }else{
                                                newsList.addAll(listClassifyListEntity);
                                            }

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    userModel.setTextNews(newsList);
                                                    userModel.setCountPage((Integer) msg.get("count"));
                                                }
                                            });

                                            Log.d(TAG, "run: newsList" + newsList);
                                        }else
                                        {
                                            Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "run:newsList 服务器请求失败:"+ msg.get("msg").toString());
                                        }
                                    } catch (JSONException | IOException | NullPointerException e ){
                                        Toast.makeText(context,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "run:newsList 服务器请求失败");
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
                    userModel.setControlGlobalRefresh(0);
                }
            }
        });*/







    }
}
