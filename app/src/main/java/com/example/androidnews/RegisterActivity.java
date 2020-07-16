package com.example.androidnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.androidnews.Util.RegexUtils;
import com.example.androidnews.entity.UsersEntity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextInputLayout TextUserName;
    private TextInputLayout TextPassword;
    private TextInputLayout TextPasswordConfirm;
    private TextInputLayout text_Email;
    private Button button;
    private EditText userName;
    private EditText password;
    private EditText PasswordConfirm;
    private EditText Email;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextUserName=findViewById(R.id.TextUserName);
        TextPassword=findViewById(R.id.TextPassword);
        TextPasswordConfirm=findViewById(R.id.TextPasswordConfirm);
        text_Email=findViewById(R.id.text_Email);
        button=findViewById(R.id.button);
        userName = TextUserName.getEditText();
        password = TextPassword.getEditText();
        PasswordConfirm = TextPasswordConfirm.getEditText();
        Email = text_Email.getEditText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userName.getText().toString().equals("")&&!password.getText().toString().equals("")&&!PasswordConfirm.getText().toString().equals("")&&!Email.getText().toString().equals("")){
                    TextUserName.setErrorEnabled(false);
                    TextPassword.setErrorEnabled(false);

                    button.setText("注册中...");
                    button.setEnabled(false);

                    final HttpHelper instance=HttpHelper.getInstance();
                    UsersEntity user=new UsersEntity();
                    user.setUserName(userName.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setEmail(Email.getText().toString());
                    instance.postDataAsyn(Global.apiUrl+"insertUser", JSON.toJSONString(user), new HttpHelper.NetCall(){

                        @Override
                        public void success(Call call, final Response response) throws IOException {

                            new Thread(new  Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        final HashMap<String,Object> msg= JSONObject.parseObject(response.body().string(), new TypeReference<HashMap<String, Object>>(){});
                                        if(!(Boolean) msg.get("flag")) {
                                            Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            // Toast.makeText(context,(String) msg.get("msg"), Toast.LENGTH_SHORT).show();
                                            runOnUiThread(new  Runnable() {

                                                @Override
                                                public void run() {
                                                    Intent intent =getIntent();
                                                    Bundle bundle =new Bundle();
                                                    bundle.putString("msg","注册成功!");
                                                    bundle.putString("userName",userName.getText().toString());
                                                    intent.putExtras(bundle);
                                                    setResult(Global.resultOK,intent);
                                                    finish();
                                                }
                                            });

                                        }
                                        //Log.d(TAG, "run: response"+response.body().string());
                                        //Toast.makeText(context,(String) response.body().string() ,Toast.LENGTH_SHORT).show();
                                    } catch (JSONException |IOException e){
                                        runOnUiThread(new  Runnable() {

                                            @Override
                                            public void run() {
                                                Toast.makeText(context,(String) "服务器请求失败!",Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        });

                                    }
                                    runOnUiThread(new  Runnable() {

                                        @Override
                                        public void run() {
                                            button.setText("注  册");
                                            button.setEnabled(true);
                                        }
                                    });

                                }
                            }).start();
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
                else if(PasswordConfirm.getText().toString().equals("")){
                    TextPasswordConfirm.setError("不能为空");
                    TextPasswordConfirm.setErrorEnabled(true);
                }
                else if(Email.getText().toString().equals("")){
                    text_Email.setError("不能为空");
                    text_Email.setErrorEnabled(true);
                }

            }
        });

        PasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    //显示错误提示
                    TextPasswordConfirm.setError("不能为空");
                    TextPasswordConfirm.setErrorEnabled(true);
                } else if (s.equals(password.getText().toString())){
                    TextPasswordConfirm.setError("密码不一致");
                    TextPasswordConfirm.setErrorEnabled(true);
                }  else{
                    TextPasswordConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    //显示错误提示
                    text_Email.setError("不能为空");
                    text_Email.setErrorEnabled(true);
                } else if(!RegexUtils.isEmail(s)){
                    text_Email.setError("请输入正确邮箱格式");
                    text_Email.setErrorEnabled(true);

                }else{
                    text_Email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
