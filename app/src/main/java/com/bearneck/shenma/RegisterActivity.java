package com.bearneck.shenma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bearneck.parking.Bean.User;
import com.bearneck.parking.DAO.CommDAO;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText accountInput = (EditText) findViewById(R.id.accountInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        final EditText realNameInput = (EditText) findViewById(R.id.nameInput);
        Button registe = (Button) findViewById(R.id.Registe);
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String account=  accountInput.getText().toString();
                String password= passwordInput.getText().toString();
                String realname=  realNameInput.getText().toString();
                User user = new User(account,password,realname);
                if (account.equals("")){
                    Toast.makeText(RegisterActivity.this,"账号不能为空！",Toast.LENGTH_SHORT).show();

                }else if (password.equals("")){
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (realname.equals("")){
                    Toast.makeText(RegisterActivity.this,"真实姓名不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    checkRegister(user);
                }
            }
        });

    }
    private void checkRegister(User u) {//检查函数
        RegisterActivity.DBThread dt = new RegisterActivity.DBThread();
        dt.setUser(u);
        dt.setContext(this);
        Thread thread = new Thread(dt);
        thread.start();
    }
    class DBThread implements Runnable {//连接数据库的线程类
        private User user;
        private Context context;

        public void setUser(User user) {
            this.user = user;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            int id= CommDAO.register(user);
            if (id == 1) {
                Intent intent1 =  new Intent();//登录页面跳转
                Bundle bundle = new Bundle();//传值
                bundle.putString(MainActivity.UserName,user.getUserName());

                intent1.putExtras(bundle);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setClass(RegisterActivity.this,MainActivity.class);
                startActivity(intent1);
                Looper.prepare();
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
            else if (id == 0){
                Looper.prepare();
                Toast.makeText(RegisterActivity.this,"用户名已经存在",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else if (id == -2){
                Looper.prepare();
                Toast.makeText(RegisterActivity.this,"连接超时",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }}
}
