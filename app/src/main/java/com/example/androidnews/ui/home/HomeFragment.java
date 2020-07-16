package com.example.androidnews.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.example.androidnews.NavigationAdapter;
import com.example.androidnews.NewsListAdapter;
import com.example.androidnews.R;
import com.example.androidnews.Util.Global;
import com.example.androidnews.entity.ClassifyListEntity;
import com.example.androidnews.entity.NewsListEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG ="HomeFragment" ;
    private Context context=this.getActivity();

    private List<ClassifyListEntity> ClassifyList = new ArrayList<ClassifyListEntity>();
    private List<NewsListEntity> NewsList = new ArrayList<NewsListEntity>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewNews;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private  HomeViewModel homeViewModel;
    private NewsListAdapter newsListAdapter;
    private RecyclerAdapterWithHF mNewsListAdapter;
    private Handler handler = new Handler();
    private  int page=1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerView=root.findViewById(R.id.recycler_navigation_view);
        recyclerViewNews=root.findViewById(R.id.recycler_news_view);

        //ptrClassicFrameLayout = root.findViewById(R.id.test_list_view_frame);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        homeViewModel.mutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<ClassifyListEntity>>() {
            @Override
            public void onChanged(List<ClassifyListEntity> classifyListEntities) {
                //Log.d(TAG, "onChanged: classifyListEntities"+classifyListEntities);
                ClassifyList=classifyListEntities;
                //Log.d(TAG, "onChanged: ClassifyList"+ClassifyList);
                LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);   //设置为横向布局
                recyclerView.setLayoutManager(layoutManager);
                NavigationAdapter adapter = new NavigationAdapter(ClassifyList);
                recyclerView.setAdapter(adapter);
                // Log.d(TAG, "onCreateView: ClassifyList:"+ClassifyList);
            }

        });





       /* newsListAdapter = new NewsListAdapter(NewsList);
        mNewsListAdapter = new RecyclerAdapterWithHF((RecyclerView.Adapter)newsListAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setAdapter(mNewsListAdapter);*/

        homeViewModel.mutableLiveDataNews.observe(getViewLifecycleOwner(), new Observer<List<NewsListEntity>>() {
            @Override
            public void onChanged(List<NewsListEntity> newsListEntities) {
                NewsList=newsListEntities;
                newsListAdapter = new NewsListAdapter(NewsList);
                LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                recyclerViewNews.setLayoutManager(layoutManager);
                recyclerViewNews.setAdapter(newsListAdapter);
                Log.d(TAG, "onChanged: NewsList"+NewsList);
            }
        });

        /*ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        //NewsList.clear();

                        homeViewModel.setControlGlobalRefresh(page);

                        Log.d(TAG, "run: NewsList"+NewsList);
                        mNewsListAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);  //开启下拉刷新
                        homeViewModel.ControlGlobalRefresh.observe(getViewLifecycleOwner(), new Observer<Integer>() {

                            @Override
                            public void onChanged(Integer integer) {
                                if(integer==0){
                                    Log.d(TAG, "onChanged: 刷新完成");

                                }
                            }
                        });
                        //Log.d(TAG, " setPtrHandler run: NewsList："+NewsList);
                    }
                }, 1500);
            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                     *//*   mData.add(new String("  RecyclerView item  - add " + page));
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(RecyclerViewActivity.this, "load more complete", Toast.LENGTH_SHORT).show();*//*
                        *//*NewsListEntity newsListEntity=new NewsListEntity();
                        newsListEntity.setNewsTitle("shdkasdhkadjha");
                        NewsList.add(newsListEntity);
                        mNewsListAdapter.notifyDataSetChanged();
                        Log.d(TAG, "NewsList"+NewsList);*//*
                        if(page<=((homeViewModel.getCountPage().getValue()/ Global.limit)+1)){
                            Log.d(TAG, "run:page= "+homeViewModel.getCountPage().getValue()/ Global.limit);
                            homeViewModel.setControlGlobalRefresh(++page);
                            mNewsListAdapter.notifyDataSetChanged();
                            ptrClassicFrameLayout.loadMoreComplete(true);
                            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        }else{
                            ptrClassicFrameLayout.loadMoreComplete(true);
                            Toast.makeText(getActivity(), "到底了", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1000);
            }
        });*/
        return root;
    }
    public void onStart() {
        //这里拿到的ViewModel实例,其实是和Activity中创建的是一个实例
        super.onStart();



        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<ClassifyListEntity>>() {
            @Override
            public void onChanged(List<ClassifyListEntity> classifyListEntities) {
                Log.d(TAG, "onChanged: classifyListEntities"+classifyListEntities);
                ClassifyList.addAll(classifyListEntities);
            }
        });*/
    }
}
