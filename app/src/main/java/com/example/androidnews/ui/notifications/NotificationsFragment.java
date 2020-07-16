package com.example.androidnews.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidnews.MainActivity;
import com.example.androidnews.R;
import com.example.androidnews.Util.Global;
import com.example.androidnews.entity.UsersEntity;

public class NotificationsFragment extends Fragment {
    public static final int RESULT_OK= -1;
    private static final String TAG = "NotificationsFragment";
    private NotificationsViewModel notificationsViewModel;
    private TextView text_userNameT;
    private TextView text_EmailT;
    private Button button2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        text_userNameT = root.findViewById(R.id.text_userNameT);
        text_EmailT= root.findViewById(R.id.text_EmailT);
        button2= root.findViewById(R.id.button2);
        if(Global.userId<0){
            text_userNameT.setText("未登录");
            button2.setText("请登录");
            button2.setText("请登录");
        }else{
            text_userNameT.setText(Global.userInfo.getUserName());
            text_EmailT.setText(Global.userInfo.getEmail());
            button2.setText("退出登录");
        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.userId<0){
                    Intent intent=new Intent(v.getContext(), MainActivity.class);
                    //intent.putExtra("newsId", news.getNewsId());
                    startActivityForResult(intent,Global.PASS_1);
                }else{
                    Toast.makeText(getContext(),"退出成功",Toast.LENGTH_SHORT).show();
                    Global.userId=-1;
                    Global.userInfo=new UsersEntity();
                    text_userNameT.setText("未登录");
                    text_EmailT.setText("");
                    button2.setText("请登录");
                }
            }
        });

       /* notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Log.d(TAG, "onActivityResult: 返回");
        Log.d(TAG, "onActivityResult:requestCode "+requestCode+"   resultCode"+resultCode);
        if(requestCode==Global.PASS_1 && resultCode==Global.resultOK){
            text_userNameT.setText(Global.userInfo.getUserName());
            text_EmailT.setText(Global.userInfo.getEmail());
            button2.setText("退出登录");
        }

    }
}
