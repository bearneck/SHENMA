package com.bearneck.shenma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bearneck.parking.Bean.User;
import com.bearneck.parking.DAO.CommDAO;

import cn.bmob.v3.Bmob;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "Your Application ID");
        setContentView(R.layout.activity_login);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        //隐藏返回按钮
        Button register = (Button) findViewById(R.id.Registe);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        final EditText accountInput = (EditText) findViewById(R.id.accountInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String account=  accountInput.getText().toString();
                String password= passwordInput.getText().toString();
                User user = new User(account,password);
//                Log.d("账号和密码", "1234567"+account+password);
                if (account.equals("")){
                    Toast.makeText(LoginActivity.this,"账号不能为空！",Toast.LENGTH_SHORT).show();

                }else if (password.equals("")){
                    Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
//                    checkLogin(user);
                    BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
                    bmobQuery.getObject("6b6c11c537", new >QueryListener<Person>() {
                        @Override
                        public void done(Person object,BmobException e) {
                            if(e==null){
                                toast("查询成功");
                            }else{
                                toast("查询失败：" + e.getMessage());
                            }
                        }
                    });

                }
            }
        });
    }
//
//    private void checkLogin(User u) {//检查函数
//        DBThread dt = new DBThread();
//        dt.setUser(u);
//        dt.setContext(this);
//        Thread thread = new Thread(dt);
//        thread.start();
//    }
//    class DBThread implements Runnable {//连接数据库的线程类
//        private User user;
//        private Context context;
//
//        public void setUser(User user) {
//            this.user = user;
//        }
//
//        public void setContext(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void run() {
//            int id= CommDAO.login(user);
//            Log.d("233", "run: "+id);
//            if (id != -1 && id != -2) {
//                Intent intent1 =  new Intent();//登录页面跳转
//                Bundle bundle = new Bundle();//传值
//                bundle.putString(MainActivity.UserName,user.getUserName());
//                intent1.putExtras(bundle);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.setClass(LoginActivity.this,MainActivity.class);
//                startActivity(intent1);
//                Looper.prepare();
//                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
//                Looper.loop();
//
//            }
//            else if (id == -1){
//                Looper.prepare();
//                Toast.makeText(LoginActivity.this,"账户或者用户名错误",Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//            else if (id == -2){
//                Looper.prepare();
//                Toast.makeText(LoginActivity.this,"连接超时",Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }}
}



