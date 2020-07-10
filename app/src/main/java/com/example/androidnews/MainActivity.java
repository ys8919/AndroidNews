package com.example.androidnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.androidnews.Util.Global;
import com.example.androidnews.Util.HttpHelper;
import com.example.androidnews.entity.UsersEntity;
import com.google.android.material.textfield.TextInputLayout;


import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextInputLayout TextUserName;
    private TextInputLayout TextPassword;
    private Button button;
    private EditText userName;
    private EditText password;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextUserName=findViewById(R.id.TextUserName);
        TextPassword=findViewById(R.id.TextPassword);
        button=findViewById(R.id.button);
        userName = TextUserName.getEditText();
        password = TextPassword.getEditText();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userName.getText().toString().equals("")&&!password.getText().toString().equals("")){
                    TextUserName.setErrorEnabled(false);
                    TextPassword.setErrorEnabled(false);

                    button.setText("登录中...");
                    button.setEnabled(false);

                    final HttpHelper instance=HttpHelper.getInstance();
                    UsersEntity user=new UsersEntity();
                    user.setUserName(userName.getText().toString());
                    user.setPassword(password.getText().toString());
                    instance.postDataAsyn(Global.apiUrl+"login", JSON.toJSONString(user), new HttpHelper.NetCall(){

                        @Override
                        public void success(Call call, final Response response) throws IOException {

                            runOnUiThread(new  Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});
                                        if(!(Boolean) msg.get("flag")) {
                                            Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(context,IndexActivity.class);
                                            instance.setToken((String)msg.get("token"));
                                            startActivity(intent);
                                        }
                                        //Log.d(TAG, "run: response"+response.body().string());
                                        //Toast.makeText(context,(String) response.body().string() ,Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }catch (JSONException e){
                                        Toast.makeText(context,(String) "服务器请求失败",Toast.LENGTH_SHORT).show();
                                    }
                                    button.setText("登  录");
                                    button.setEnabled(true);
                                }
                            });
                        }

                        @Override
                        public void failed(Call call, final IOException e) {

                            runOnUiThread(new  Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(context,"服务器请求失败",Toast.LENGTH_SHORT).show();
                                    button.setText("登  录");
                                    button.setEnabled(true);
                                }
                            });
                        }
                    });
                }else if(userName.getText().toString().equals("")){
                    TextUserName.setError("不能为空");
                    TextUserName.setErrorEnabled(true);
                }else if(password.getText().toString().equals("")){
                    TextPassword.setError("不能为空");
                    TextPassword.setErrorEnabled(true);
                }

            }
        });


        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    //显示错误提示
                    TextUserName.setError("不能为空");
                    TextUserName.setErrorEnabled(true);
                } else {
                    TextUserName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    //显示错误提示
                    TextPassword.setError("不能为空");
                    TextPassword.setErrorEnabled(true);
                } else {
                    TextPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
