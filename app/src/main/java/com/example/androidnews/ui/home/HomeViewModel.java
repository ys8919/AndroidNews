package com.example.androidnews.ui.home;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.androidnews.IndexActivity;
import com.example.androidnews.Util.Global;
import com.example.androidnews.Util.HttpHelper;
import com.example.androidnews.entity.ClassifyListEntity;
import com.example.androidnews.entity.NewsListEntity;
import com.example.androidnews.entity.UsersEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    public MutableLiveData<List<ClassifyListEntity>> mutableLiveData;
    public MutableLiveData<List<NewsListEntity>> mutableLiveDataNews;
    public MutableLiveData<Integer> ControlGlobalRefresh;   //>=1 请求刷新 0 刷新完成
    public MutableLiveData<Integer> countPage;   //>=1 请求刷新 0 刷新完成
    public HomeViewModel() {
        mutableLiveData = new MutableLiveData<>();
        mutableLiveDataNews=new MutableLiveData<>();
        ControlGlobalRefresh=new MutableLiveData<>();
        countPage=new MutableLiveData<>();
    }
    public LiveData<Integer> getCountPage(){
        return countPage;
    }
    public void setCountPage( Integer c){
        countPage.setValue(c);
        // Log.d(TAG, "setText:ClassifyList "+ClassifyList);
    }
    public LiveData<Integer> getControlGlobalRefresh(){
        return ControlGlobalRefresh;
    }
    public void setControlGlobalRefresh( Integer c){
        ControlGlobalRefresh.setValue(c);
        // Log.d(TAG, "setText:ClassifyList "+ClassifyList);
    }
    public LiveData<List<ClassifyListEntity>> getText() {
        //Log.d(TAG, "getText: mText "+mText.getValue().toString());
        return mutableLiveData;
    }
    public LiveData<List<NewsListEntity>> getTextNwes() {
        //Log.d(TAG, "getText: mText "+mText.getValue().toString());
        return mutableLiveDataNews;
    }

    public void setText( List<ClassifyListEntity> ClassifyList){
        mutableLiveData.setValue(ClassifyList);
       // Log.d(TAG, "setText:ClassifyList "+ClassifyList);
    }
    public void setTextNews( List<NewsListEntity> NewsList){
        mutableLiveDataNews.setValue(NewsList);
         Log.d(TAG, "setText:NewsList "+NewsList);
    }
}