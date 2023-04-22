package com.project.stopdistraction;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_HOME;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_INFO_SRC;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_POMODORO;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_Schedule;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_TODO_TASK;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_TRANC_DETAILS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.project.stopdistraction.common.MessageReceiver;
import com.project.stopdistraction.common.MyApplication;
import com.project.stopdistraction.common.UIHelper;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.roomdb.Message;
import com.project.stopdistraction.roomdb.MessageDao;
import com.project.stopdistraction.roomdb.UserDatabase;
import com.project.stopdistraction.ui.DailyTaskFragment;
import com.project.stopdistraction.ui.HomeFragment;
import com.project.stopdistraction.ui.InformationSrcFragment;
import com.project.stopdistraction.ui.ScheduleMesageFragment;
import com.project.stopdistraction.ui.TodoTaskFragment;
import com.project.stopdistraction.ui.TransantionDetailsFragment;
import com.project.stopdistraction.ui.pomodoroFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout ll_nav_drawer_menu;
    private DrawerLayout drawer;
    LinearLayout ll_back;
    Context context;
    MessageReceiver messageReceiver = new MessageReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_back = findViewById(R.id.ll_back);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ll_nav_drawer_menu = (LinearLayout) findViewById(R.id.ll_nav_drawer_menu);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        context=getApplicationContext();
        UIHelper.getInstance().replaceFragment(this, HomeFragment.newInstance(), TAG_FRAGMENT_HOME, R.id.home_container,
                true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);

        //Drawer Close and Open
        ll_nav_drawer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        ll_back.setOnClickListener(this);

    }

    public static void messageRecived(String senderName,String message){
        Log.d(">>","Message Received");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        if(message.toLowerCase().contains("credited")){
            new adduserAsyncTask(message, senderName,"Credited",formattedDate).execute();
        }else if(message.toLowerCase().contains("debited")){
            new adduserAsyncTask(message, senderName,"Debited",formattedDate).execute();
        }


    }
    private static class adduserAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private String message;
        private String messageType;
        private String senderName;
        private String date;
        private Context context;

        private DailyTask new_dailyTask;
        MessageDao userDao;
        private Boolean is_new;

        private adduserAsyncTask(String message,String senderName, String messageType, String date) {
            this.message = message;
            this.messageType = messageType;
            this.senderName = senderName;
            this.date = date;

            UserDatabase db = UserDatabase.getInstance(MyApplication.getAppContext());
            this.userDao = db.msgDao();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            new_dailyTask = null;
            if (new_dailyTask == null) {
                userDao.insertUser(new Message(this.message, this.messageType, this.date,this.senderName));
            } else {
                is_new = false;
            }
            return is_new;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            if (is_new) {
//                Toast.makeText(getActivity(), "Daily Task Added", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Task Already Exists", Toast.LENGTH_SHORT).show();
//            }
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(messageReceiver, filter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(messageReceiver);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                UIHelper.getInstance().replaceFragment(this, HomeFragment.newInstance(), TAG_FRAGMENT_HOME, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
            case R.id.nav_info_src:
                UIHelper.getInstance().replaceFragment(this, InformationSrcFragment.newInstance(), TAG_FRAGMENT_INFO_SRC, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
            case R.id.nav_pomodoro:
                UIHelper.getInstance().replaceFragment(this, pomodoroFragment.newInstance(), TAG_FRAGMENT_POMODORO, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
//            case R.id.nav_dailyTask:
//                UIHelper.getInstance().replaceFragment(this, DailyTaskFragment.newInstance(), TAG_FRAGMENT_DAILY_TASK, R.id.home_container,
//                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
//                drawer.closeDrawer(GravityCompat.END);
//                break;
            case R.id.nav_todo:
                UIHelper.getInstance().replaceFragment(this, TodoTaskFragment.newInstance(), TAG_FRAGMENT_TODO_TASK, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
            case R.id.nav_Schedule:
                UIHelper.getInstance().replaceFragment(this, ScheduleMesageFragment.newInstance(), TAG_FRAGMENT_Schedule, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
            case R.id.nav_transaction:
                UIHelper.getInstance().replaceFragment(this, TransantionDetailsFragment.newInstance(), TAG_FRAGMENT_TRANC_DETAILS, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
        }
        return true;
    }
}